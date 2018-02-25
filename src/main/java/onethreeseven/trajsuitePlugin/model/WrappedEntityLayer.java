package onethreeseven.trajsuitePlugin.model;


import java.util.Iterator;

/**
 * A layer of {@link WrappedEntity}
 * @author Luke Bermingham
 */
public class WrappedEntityLayer<T> extends EntityLayer<WrappedEntity<T>> {

    public WrappedEntityLayer(String layerName, Class<T> modelType) {
        super(layerName, modelType);
    }

    public WrappedEntity<T> get(String id){
        return this.entities.get(id);
    }

    @Override
    public Iterator<WrappedEntity<T>> iterator() {
        return super.iterator();
    }

    @Override
    public WrappedEntity<T> remove(String id) {
        return super.remove(id);
    }
}
