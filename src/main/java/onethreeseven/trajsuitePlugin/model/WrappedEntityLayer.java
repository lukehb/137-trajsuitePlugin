package onethreeseven.trajsuitePlugin.model;


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


}
