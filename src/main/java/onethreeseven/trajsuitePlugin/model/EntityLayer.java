package onethreeseven.trajsuitePlugin.model;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A layer of {@link Entity}
 * @author Luke Bermingham
 */
public class EntityLayer<T extends Entity> implements Selectable, Iterable<T> {

    protected final ConcurrentHashMap<String, T> entities;
    private String layerName;

    public EntityLayer(String layerName){
        this.entities = new ConcurrentHashMap<>();
        this.layerName = layerName;
    }

    public void add(T entity){
        entities.put(entity.getId(), entity);
    }

    public Entity get(String entityId){
        return this.entities.get(entityId);
    }

    @Override
    public void setSelected(boolean selected) {
        for (Entity entity : this) {
            entity.setSelected(selected);
        }
    }

    @Override
    /**
     * For a layer to be selected, all of its children must be selected.
     */
    public boolean isSelected() {
        for (Entity t : this) {
            if(!t.isSelected()){
                return false;
            }
        }
        return true;
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

    @Override
    public String toString() {
        return layerName;
    }
}
