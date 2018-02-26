package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import onethreeseven.trajsuitePlugin.util.IdGenerator;
import java.util.*;

/**
 * The manager of all layers and persistent entities in the program.
 * @author Luke Bermingham
 */
public class Layers implements Iterable<WrappedEntityLayer> {

    private ReadOnlyObjectWrapper<WrappedEntity> entityChanged = new ReadOnlyObjectWrapper<>();

    /**
     * Layers are per class type.
     */
    protected final HashMap<Class, WrappedEntityLayer> allLayers;


    public Layers(){
        this.allLayers = new HashMap<>();
    }

    public <T> WrappedEntity<T> add(String id, T model){
        VisibleEntity<T> entity = new VisibleEntity<>(id, model, true);
        Class<?> modelType = model.getClass();
        WrappedEntityLayer layer = allLayers.get(modelType);

        if(layer == null){
            layer = newEntityLayer(modelType);
            allLayers.put(modelType, layer);
        }
        layer.add(entity);

        //update property for the listeners
        entityChanged.setValue(entity);

        return entity;
    }

    public <T> WrappedEntity<T> add(T model){
        return this.add(IdGenerator.nextId(), model);
    }

    private <T> WrappedEntityLayer<T> newEntityLayer(Class<T> modelType){
        return new VisibleEntityLayer<>(modelType.getSimpleName(), modelType, true);
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

    /**
     * Removes an entity from a layer, if it exists.
     * Note, if removing the entity empties a layer, the layer is removed too.
     * @param modelType The type of the entity.
     * @param id The id of the entity.
     * @param <T> The type of the entity.
     * @return True if the entity was removed, otherwise, false.
     */
    public <T> boolean remove(Class<T> modelType, String id){
        WrappedEntityLayer layer = allLayers.get(modelType);
        if(layer != null){
            WrappedEntity toRemove = layer.get(id);
            return remove(toRemove);
        }
        return false;
    }

    /**
     * Removes an entity from a layer, if it exists.
     * Note, if removing the entity empties a layer, the layer is removed too.
     * @param entity The entity to remove.
     * @return True if the entity was removed, otherwise, false.
     */
    public boolean remove(WrappedEntity entity){
        Class modelType = entity.model.getClass();
        WrappedEntityLayer layer = allLayers.get(entity.model.getClass());
        if(layer != null){
            WrappedEntity removedEntity = layer.remove(entity.getId());
            boolean removed = removedEntity != null;
            if(removed){
                //remove the layer too if it is now empty
                if(layer.size() == 0){
                    allLayers.remove(modelType);
                }
                //let the listeners of this property know
                entityChanged.set(removedEntity);
            }
            return removed;
        }
        return false;
    }

    public boolean removeLayer(Class modelType){
        WrappedEntityLayer layer = this.allLayers.get(modelType);
        if(layer != null){
            boolean removed = this.allLayers.remove(modelType) != null;
            if(removed){
                if(layer.size() > 0){
                    entityChanged.set((WrappedEntity) layer.iterator().next());
                }else{
                    entityChanged.set(null);
                }
            }
            return removed;
        }
        return false;
    }

    public ReadOnlyObjectProperty<WrappedEntity> entityChangedProperty() {
        return entityChanged.getReadOnlyProperty();
    }
}
