package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import onethreeseven.trajsuitePlugin.graphics.GraphicsPayload;
import onethreeseven.trajsuitePlugin.transaction.*;
import onethreeseven.trajsuitePlugin.util.IdGenerator;

import java.util.*;

/**
 * The manager of all layers and persistent entities in the program.
 * <p>
 * The general design is we are storing models of certain types in named layers. The models are stored in layers using their ids
 * and each layer has models that are all the same model type.
 * </p>
 * <p>
 * Thus, to retrieve models from this object the fastest approach is to use models with ids and layers with names. Doing so allows you
 * to use the relevant methods to retrieve a specific model quickly.
 * </p>
 * @author Luke Bermingham
 */
public class Layers implements Iterable<WrappedEntityLayer> {



    public final ReadOnlyObjectProperty<AddEntitiesTransaction> addEntitiesTransactionProperty;
    protected final ReadOnlyObjectWrapper<AddEntitiesTransaction> addEntitiesTransactionInternal;

    public final ReadOnlyObjectProperty<RemoveEntitiesTransaction> removeEntitiesTransactionProperty;
    protected final ReadOnlyObjectWrapper<RemoveEntitiesTransaction> removeEntitiesTransactionInternal;

    public final ReadOnlyObjectProperty<Integer> numEditedEntitiesProperty;
    protected final ReadOnlyObjectWrapper<Integer> numEditedEntitiesInternal;

    protected final AccumulatingEditHistory accumulator;

    /**
     * Layers are per class type.
     */
    protected final Map<String, WrappedEntityLayer> allLayers;


    public Layers(){
        this.allLayers = new HashMap<>();
        this.addEntitiesTransactionInternal = new ReadOnlyObjectWrapper<>(null);
        this.addEntitiesTransactionProperty = addEntitiesTransactionInternal.getReadOnlyProperty();
        this.removeEntitiesTransactionInternal = new ReadOnlyObjectWrapper<>(null);
        this.removeEntitiesTransactionProperty = removeEntitiesTransactionInternal.getReadOnlyProperty();
        this.numEditedEntitiesInternal = new ReadOnlyObjectWrapper<>(0);
        this.numEditedEntitiesProperty = numEditedEntitiesInternal.getReadOnlyProperty();

        //on accumulation of edits after 150ms, set the internal edit property to fire off listeners
        this.accumulator = new AccumulatingEditHistory(150, numEditedEntitiesInternal::set);
    }

    public WrappedEntityLayer getLayer(String layername) {
        return allLayers.get(layername);
    }

    /**
     * Get all models in the layer with the specified name .
     * @param layername The name of the layer.
     * @return A collection of models of the specified type.
     */
    public Collection getLayerModels(String layername){

        WrappedEntityLayer layer = allLayers.get(layername);
        if(layer != null){
            return getAllModelsIn(layer);
        }
        return new ArrayList<>();
    }

    protected Collection getAllModelsIn(WrappedEntityLayer layer){
        ArrayList<Object> models = new ArrayList<>();
        for (WrappedEntity entity : layer) {
            Object model = entity.model;
            models.add(model);
        }
        return models;
    }

    /**
     * Get the first layer with the specified model type.
     * @param modelType The specified model type.
     * @return A layer containing a model of the specified model type, or null, if no such layer exists.
     */
    public WrappedEntityLayer getFirstLayerContaining(Class<?> modelType){
        for (WrappedEntityLayer layer : allLayers.values()) {
            for (WrappedEntity wrappedEntity : layer) {
                if(wrappedEntity.model.getClass().equals(modelType)){
                    return layer;
                }
            }
        }
        return null;
    }

    public void process(AddEntitiesTransaction transaction){

        Map<String, Map<String, WrappedEntity>> entitiesToAdd = new HashMap<>();
        Collection<AddEntityUnit> units = transaction.getData();

        for (AddEntityUnit unit : units) {
            Map<String, WrappedEntity> growingMap = entitiesToAdd.computeIfAbsent(unit.getLayername(), k -> new HashMap<>());
            WrappedEntity<?> entity;
            if(unit instanceof AddBoundingEntityUnit){
               entity = newEntity(unit.getEntityId(), unit.getModel(), ((AddBoundingEntityUnit) unit).getPayload());
            }
            else{
                entity = newEntity(unit.getEntityId(), unit.getModel());
            }
            registerAllPropertyChangedEvents(entity);
            growingMap.put(unit.getEntityId(), entity);
        }

        for (Map.Entry<String, Map<String, WrappedEntity>> entry : entitiesToAdd.entrySet()) {
            WrappedEntityLayer existingLayer = allLayers.get(entry.getKey());
            //there is no existing layer, so make one and accumulate it the layers map
            if(existingLayer == null){
                existingLayer = newEntityLayer(entry.getKey(), entry.getValue());
                allLayers.put(entry.getKey(), existingLayer);
            }
            //layer already exists, so just put all new entities in at once
            else{
                existingLayer.entities.putAll(entry.getValue());
            }
        }

        //fire off transaction change
        addEntitiesTransactionInternal.set(transaction);

    }

    public void process(RemoveEntitiesTransaction transaction){

        for (RemoveEntityUnit removeEntityUnit : transaction.getData()) {
           String layername = removeEntityUnit.getLayername();
           WrappedEntityLayer existingLayer = allLayers.get(layername);
           if(existingLayer != null){
               //remove the entity
               WrappedEntity removedEntity = existingLayer.entities.remove(removeEntityUnit.getId());
               if(removedEntity != null){
                   unRegisterPropertyChangedEvents(removedEntity);
               }
               //if layer is now empty after removal, remove the layer too
               if(existingLayer.entities.isEmpty()){
                   this.allLayers.remove(layername);
               }
           }
        }

        //set this so listeners get fired
        this.removeEntitiesTransactionInternal.set(transaction);
    }

    public void removeLayer(String id){
        WrappedEntityLayer layer = allLayers.get(id);
        if(layer == null){
            return;
        }
        //make remove transaction
        RemoveEntitiesTransaction transaction = new RemoveEntitiesTransaction();
        for (WrappedEntity wrappedEntity : layer) {
            transaction.add(layer.getLayerName(), wrappedEntity.getId());
        }
        //process the removed entities transaction
        process(transaction);
    }

    ///////////////////////
    //Change listeners
    ///////////////////////

    protected final ChangeListener<? super Boolean> selectionChanged = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            accumulator.accumulate();
        }
    };

    protected final ChangeListener<? super Boolean> visibilityChanged = new ChangeListener<>() {
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            accumulator.accumulate();
        }
    };


    protected void registerAllPropertyChangedEvents(WrappedEntity entity){
        entity.isSelectedProperty().addListener(selectionChanged);
        if(entity instanceof VisibleEntity){
            ((VisibleEntity) entity).isVisibleProperty().addListener(visibilityChanged);
        }
    }

    protected void unRegisterPropertyChangedEvents(WrappedEntity entity){
        entity.isSelectedProperty().removeListener(selectionChanged);
        if(entity instanceof VisibleEntity){
            ((VisibleEntity) entity).isVisibleProperty().removeListener(visibilityChanged);
        }
    }

    protected <T> WrappedEntity<T> newEntity(String entityId, T model, GraphicsPayload graphicsPayload){
        return new WrappedEntity<>(entityId, model);
    }

    protected <T> WrappedEntity<T> newEntity(String entityId, T model){
        return new WrappedEntity<>(entityId, model);
    }

    protected WrappedEntityLayer newEntityLayer(String layerName, Map<String, WrappedEntity> entities){
        return new WrappedEntityLayer(layerName, entities);
    }

    ///////////////////////////////
    ///Add and remove shortcuts
    ///////////////////////////////
    public void remove(String layername, String entityId){
        RemoveEntitiesTransaction transaction = new RemoveEntitiesTransaction();
        transaction.add(layername, entityId);
        process(transaction);
    }

    public void add(String layername, String entityId, Object model){
        AddEntitiesTransaction transaction = new AddEntitiesTransaction();
        transaction.add(layername, entityId, model);
        process(transaction);
    }

    public void add(String layername, Object model){
        this.add(layername, IdGenerator.nextId(), model);
    }

    public void add(Object model){
        this.add("Unnamed " + model.getClass().getSimpleName(), model);
    }

    /**
     * Gets and entity by its id.
     * <br> This, potentially, has to iterate all layers. A faster approach is {@link Layers#getEntity(String, String, Class)}.
     * @param entityId the entity id to look for.
     * @return The entity the id was mapped to or null if no mapping was found.
     */
    public WrappedEntity getEntity(String entityId){
        for (WrappedEntityLayer layer : allLayers.values()) {
            WrappedEntity entity = layer.get(entityId);
            if(entity != null){
                return entity;
            }
        }
        return null;
    }

    /**
     * Gets an entity by its id and model type.
     * <br> This, potentially, has to iterate all layers. A faster approach is {@link Layers#getEntity(String, String, Class)}.
     * @param entityId the entity id to look for.
     * @param modelType the type of layer to look in for the entity.
     * @param <T> The type of the model.
     * @return The entity the id was mapped to or null if no mapping was found.
     */
    public <T> WrappedEntity<T> getEntity(String entityId, Class<T> modelType){
        for (WrappedEntityLayer layer : allLayers.values()) {
            WrappedEntity entity = layer.get(entityId);
            if(entity != null && entity.model.getClass().equals(modelType)){
                return entity;
            }
        }
        return null;
    }

    public WrappedEntity getEntity(String layername, String entityId){
        WrappedEntityLayer layer = allLayers.get(layername);
        if(layer == null){
            return null;
        }
        return layer.get(entityId);
    }

    /**
     * The fastest way to get a specific entity from this object.
     * @param layername The name of the layer the entity is in.
     * @param entityId The id of the entity.
     * @param modelType The type of model the entity is wrapping.
     * @param <T> The type of the model.
     * @return A wrapped entity if there was a mapping for that layer name/id/model type, otherwise, null.
     */
    public <T> WrappedEntity<T> getEntity(String layername, String entityId, Class<T> modelType){
        WrappedEntityLayer layer = allLayers.get(layername);
        WrappedEntity entity = layer.get(entityId);
        if(entity != null && entity.model.getClass().equals(modelType)){
            return entity;
        }
        return null;
    }

    /**
     * @param modelType the type of model we want.
     * @param <T> The type of the model.
     * @return The first entity in a layer that has that model type.
     */
    //@SuppressWarnings("unchecked")
    public <T> WrappedEntity<T> getFirstEntity(Class<T> modelType){
        for (WrappedEntityLayer layer : allLayers.values()) {
            for (WrappedEntity wrappedEntity : layer) {
                if(wrappedEntity.model.getClass().equals(modelType)){
                    return wrappedEntity;
                }
            }
        }
        return null;
    }

    /**
     * Get all models that have the specified model type.
     * @param modelType The type of model you are interested in getting.
     * @param <T> The type of the model.
     * @return A collection of layers with the specified model type, or an empty collection if no such layers exist.
     */
    public <T> Collection<T> getAllModelsOfType(Class<T> modelType){
        ArrayList<T> wrappedEntities = new ArrayList<>();
        for (WrappedEntityLayer layer : allLayers.values()) {
            for (WrappedEntity wrappedEntity : layer) {
                if(wrappedEntity.model.getClass().equals(modelType)){
                    wrappedEntities.add((T) wrappedEntity);
                }
            }
        }
        return wrappedEntities;
    }

    @Override
    public Iterator<WrappedEntityLayer> iterator() {
        return this.allLayers.values().iterator();
    }

}
