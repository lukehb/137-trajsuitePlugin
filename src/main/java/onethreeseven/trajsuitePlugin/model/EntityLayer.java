package onethreeseven.trajsuitePlugin.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A layer of {@link Entity}
 * @author Luke Bermingham
 */
public class EntityLayer<T extends Entity> implements Iterable<T> {

    protected final ObservableMap<String, T> entities;
    private final Class modelType;
    private String layerName;

    public EntityLayer(String layerName, Class modelType){
        this.entities = FXCollections.observableMap(initMap());
        this.modelType = modelType;
        this.layerName = layerName;
    }

    /**
     * Implementors may wish to use other map types, such as concurrent map.
     * @return The map that the entities in this layer will be stored in.
     */
    protected Map<String, T> initMap(){
        return new HashMap<>();
    }

    public void add(T entity){
        entities.put(entity.getId(), entity);
    }

    public Entity get(String entityId){
        return this.entities.get(entityId);
    }


    public int size(){
        return entities.size();
    }

    @Override
    public Iterator<T> iterator() {
        return entities.values().iterator();
    }

    public String getLayerName() {
        return layerName;
    }

    public T remove(String id){
        return this.entities.remove(id);
    }

    @Override
    public String toString() {
        return layerName;
    }

    public Class<T> getModelType() {
        return modelType;
    }

}
