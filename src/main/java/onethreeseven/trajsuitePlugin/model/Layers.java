package onethreeseven.trajsuitePlugin.model;

import onethreeseven.trajsuitePlugin.util.IdGenerator;
import java.util.*;

/**
 * The manager of all layers and persistent entities in the program.
 * @author Luke Bermingham
 */
public class Layers implements Iterable<WrappedEntityLayer> {


    /**
     * Layers are per class type.
     */
    protected final HashMap<Class, WrappedEntityLayer> allLayers;


    public Layers(){
        this.allLayers = new HashMap<>();
    }

    public <T> WrappedEntity<T> add(String id, T model){
        WrappedEntity<T> entity = new WrappedEntity<>(id, model);
        Class<?> modelType = model.getClass();
        WrappedEntityLayer layer = allLayers.get(modelType);

        if(layer == null){
            layer = newEntityLayer(modelType);
            allLayers.put(modelType, layer);
        }
        layer.add(entity);

        return entity;
    }

    public <T> WrappedEntity<T> add(T model){
        return this.add(IdGenerator.nextId(), model);
    }

    private WrappedEntityLayer newEntityLayer(Class modelType){
        return new WrappedEntityLayer(modelType.getSimpleName());
    }

    /**
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
     * @param entityId the entity id to look for.
     * @param modelType the type of layer to look in for the entity.
     * @param <T> The type of the model.
     * @return The entity the id was mapped to or null if no mapping was found.
     */
    //@SuppressWarnings("unchecked")
    public <T> WrappedEntity<T> getEntity(String entityId, Class<T> modelType){
        WrappedEntityLayer layer = allLayers.get(modelType);
        if(layer != null){
            return layer.get(entityId);
        }
        return null;
    }

    /**
     * @param modelType the type of layer to look in for the entity.
     * @param <T> The type of the model.
     * @return The first entity in that layer type.
     */
    //@SuppressWarnings("unchecked")
    public <T> WrappedEntity<T> getFirstEntity(Class<T> modelType){
        WrappedEntityLayer layer = allLayers.get(modelType);
        if(layer != null){
            return (WrappedEntity<T>) layer.iterator().next();
        }
        return null;
    }

    /**
     * Get a layer by the type of model it holds.
     * @param modelType The type of model you are interested in getting.
     * @param <T> The type of the model.
     * @return Will return a layer if there is one or null if there isn't one.
     */
    public <T> WrappedEntityLayer<T> getLayerByType(Class<T> modelType){
        return (WrappedEntityLayer<T>) allLayers.get(modelType);
    }

    @Override
    public Iterator<WrappedEntityLayer> iterator() {
        return this.allLayers.values().iterator();
    }

}
