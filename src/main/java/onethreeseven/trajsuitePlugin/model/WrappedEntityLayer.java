package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import java.util.Iterator;

/**
 * A layer of {@link WrappedEntity}
 * @author Luke Bermingham
 */
public class WrappedEntityLayer<T> extends EntityLayer<WrappedEntity<T>> implements Selectable {

    private final ReadOnlyBooleanWrapper isSelected;

    public WrappedEntityLayer(String layerName, Class<T> modelType) {
        super(layerName, modelType);

        this.isSelected = new ReadOnlyBooleanWrapper(false);

        //setup binding so layer selection is always determined by children
        isSelected.bind(new AnyMatchingBooleanBinding<>(true, entities, WrappedEntity::isSelectedProperty));

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

    @Override
    public ReadOnlyBooleanProperty isSelectedProperty() {
        return isSelected.getReadOnlyProperty();
    }

    public void selectAll(boolean doSelect){
        for (WrappedEntity<T> entity : entities.values()) {
            entity.isSelectedProperty().set(doSelect);
        }
    }

}
