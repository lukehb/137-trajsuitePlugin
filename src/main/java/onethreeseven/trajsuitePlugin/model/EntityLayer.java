package onethreeseven.trajsuitePlugin.model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import java.util.HashMap;
import java.util.Iterator;

/**
 * A layer of {@link Entity}
 * @author Luke Bermingham
 */
public class EntityLayer<T extends Entity> implements Iterable<T> {

    protected final ObservableMap<String, T> entities;
    private final Class modelType;
    private String layerName;

    public EntityLayer(String layerName, Class modelType){
        this.entities = FXCollections.observableMap(new HashMap<>());
        this.modelType = modelType;
        this.layerName = layerName;
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
