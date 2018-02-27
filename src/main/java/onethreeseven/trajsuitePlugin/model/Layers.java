package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
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

    private ReadOnlyObjectWrapper<WrappedEntity> entityChanged = new ReadOnlyObjectWrapper<>();

    /**
     * Layers are per class type.
     */
    protected final Map<String, WrappedEntityLayer> allLayers;


    public Layers(){
        this.allLayers = new HashMap<>();
    }

    public <T> WrappedEntityLayer<T> getLayer(String layername, Class<T> modelType) {
        WrappedEntityLayer layer = allLayers.get(layername);
        if(layer != null && layer.getModelType().equals(modelType)){
            return layer;
        }
        return null;
    }

    /**
     * Get all models in the layer with the specified name and model type.
     * @param layername The name of the layer.
     * @param modelType The type of the models you want.
     * @param <T> The model type.
     * @return A collection of models of the specified type.
     */
    public <T> Collection<T> getLayerModels(String layername, Class<T> modelType){

        WrappedEntityLayer layer = allLayers.get(layername);
        if(layer != null){
            return getAllModelsIn(layer);
        }
        return new ArrayList<>();
    }

    private <T> Collection<T> getAllModelsIn(WrappedEntityLayer<T> layer){
        ArrayList<T> models = new ArrayList<>();
        for (WrappedEntity<T> entity : layer) {
            T model = entity.model;
            models.add(model);
        }
        return models;
    }

    /**
     * Get the first layer with the specified model type.
     * @param modelType The specified model type.
     * @return A layer of the specified model type, or null, if no such layer exists.
     */
    public WrappedEntityLayer getFirstLayerOfType(Class modelType){
        for (WrappedEntityLayer layer : allLayers.values()) {
            if(layer.getModelType().equals(modelType)){
                return layer;
            }
        }
        return null;
    }

    /**
     * Adds an entity with a specified id to a layer with a specified name.
     * @param layername The name of the layer.
     * @param id The id of the model.
     * @param model The model itself.
     * @param <T> The type of the model.
     * @return The entity the model is now wrapped in.
     */
    public <T> WrappedEntity<T> add(String layername, String id, T model){
        WrappedEntity<T> entity = new WrappedEntity<>(id, model);
        Class<?> modelType = model.getClass();
        WrappedEntityLayer layer = allLayers.get(layername);

        if(layer == null){
            layer = newEntityLayer(modelType, layername);
            allLayers.put(layer.getLayerName(), layer);
        }
        layer.add(entity);

        //update property for the listeners
        entityChanged.setValue(entity);

        return entity;
    }

    /**
     * Adds an entity with an id to a default-named layers for this type of model.
     * @param id The id of the model.
     * @param model The model itself.
     * @param <T> The type of the model.
     * @return The entity the model is now wrapped in.
     */
    public <T> WrappedEntity<T> add(String id, T model){
        Class<?> modelType = model.getClass();
        return this.add("Unnamed" + modelType.getSimpleName(), id, model);
    }

    /**
     * Adds a model to the layers and gives it an auto-generated id and puts it in a default named layer for this type of model.
     * @param model The model to add.
     * @param <T> The type of the model.
     * @return The entity the model becomes wrapped in.
     */
    public <T> WrappedEntity<T> add(T model){
        return this.add(IdGenerator.nextId(), model);
    }

    private <T> WrappedEntityLayer<T> newEntityLayer(Class<T> modelType, String layerName){
        return new WrappedEntityLayer<>(layerName, modelType);
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
            if(layer.getModelType().equals(modelType)){
                WrappedEntity entity = layer.get(entityId);
                if(entity != null){
                    return entity;
                }
            }
        }
        return null;
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
            if(layer.getModelType().equals(modelType) && layer.size() > 0){
                return (WrappedEntity<T>) layer.iterator().next();
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
            if(layer.getModelType().equals(modelType)){
                Collection<T> modelsIn = getAllModelsIn(layer);
                wrappedEntities.addAll(modelsIn);
            }
        }
        return wrappedEntities;
    }

    @Override
    public Iterator<WrappedEntityLayer> iterator() {
        return this.allLayers.values().iterator();
    }

    private void shouldLayerRemoveSelf(WrappedEntityLayer layer){
        if(layer.size() == 0){
            allLayers.remove(layer.getLayerName());
        }
    }

    /**
     * Remove an entity based on its id and the its model type. A faster method would be {@link Layers#remove(String, String)}
     * @param id The entity id to remove.
     * @param modelType The type of the model.
     * @return True if we removed something, otherwise, false.
     */
    public boolean remove(String id, Class modelType){
        for (WrappedEntityLayer layer : allLayers.values()) {
            if(layer.getModelType().equals(modelType)){
                WrappedEntity wrappedEntity = layer.remove(id);
                if(wrappedEntity != null){
                    shouldLayerRemoveSelf(layer);
                    entityChanged.set(wrappedEntity);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Removes the first entity we encounter with the specified id.
     * <br>
     * This, potentially, requires iterating all layers, it would be more efficient to
     * call {@link Layers#remove(String, String)}.
     * <br>
     * Note, if removing the entity empties a layer, the layer is removed too.
     * @param id The id of the entity you wish to remove.
     * @return True if the entity was removed, otherwise, false.
     */
    public boolean remove(String id){
        for (WrappedEntityLayer wrappedEntityLayer : allLayers.values()) {
            WrappedEntity wrappedEntity = wrappedEntityLayer.remove(id);
            if(wrappedEntity != null){
                shouldLayerRemoveSelf(wrappedEntityLayer);
                entityChanged.set(wrappedEntity);
                return true;
            }
        }
        return false;
    }

    /**
     * The fastest way to remove an entity from the layers.
     * Note, that if the entity is removed and the layer is now empty, the layer is removed too.
     * @param entityId The entity id.
     * @param layername The layer name.
     * @return True if we removed an entity, otherwise false.
     */
    public boolean remove(String entityId, String layername){
        WrappedEntityLayer layer = allLayers.get(layername);
        if(layer != null){
            WrappedEntity entity = layer.remove(entityId);
            if(entity != null){
                shouldLayerRemoveSelf(layer);
                entityChanged.set(entity);
                return true;
            }
        }
        return false;
    }

    /**
     * The fastest way to remove a layer.
     * @param layername The name of the layer to remove.
     * @return True if we removed a layer with this name, otherwise, false.
     */
    public boolean removeLayer(String layername){
        WrappedEntityLayer removedLayer = this.allLayers.remove(layername);
        if(removedLayer != null){
            entityChanged.set(null);
            return true;
        }
        return false;
    }

    public ReadOnlyObjectProperty<WrappedEntity> entityChangedProperty() {
        return entityChanged.getReadOnlyProperty();
    }
}
