package onethreeseven.trajsuitePlugin.model;


import java.util.Iterator;

/**
 * A layer of {@link WrappedEntity}
 * @author Luke Bermingham
 */
public class WrappedEntityLayer<T> extends EntityLayer<WrappedEntity<T>> {

    public WrappedEntityLayer(String layerName) {
        super(layerName);
    }

    public WrappedEntity<T> get(String id){
        return this.entities.get(id);
    }

    @Override
    public Iterator<WrappedEntity<T>> iterator() {
        return super.iterator();
    }
}
