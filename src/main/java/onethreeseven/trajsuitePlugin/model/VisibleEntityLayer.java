package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.*;

/**
 * A {@link WrappedEntityLayer} that can toggle its visibility.
 * Note, toggling visibility effects all children, and likewise children can effect
 * the layers visibility (e.g. if they are all off the layer turns off).
 * @author Luke Bermingham
 */
public class VisibleEntityLayer<T> extends WrappedEntityLayer<T> implements Visible {

    private final ReadOnlyBooleanWrapper isVisible;

    public VisibleEntityLayer(String layerName, Class<T> modelType, boolean isVisible) {
        super(layerName, modelType);
        this.isVisible = new ReadOnlyBooleanWrapper(isVisible);

        //setup binding so layer selection is always determined by children
        this.isVisible.bind(new AnyMatchingBooleanBinding<>(true, entities, tWrappedEntity -> {
            if(tWrappedEntity instanceof VisibleEntity){
                return ((VisibleEntity) tWrappedEntity).isVisibleProperty();
            }
            return null;
        }));
    }

    @Override
    public ReadOnlyBooleanProperty isVisibleProperty() {
        return isVisible.getReadOnlyProperty();
    }

    @Override
    public void add(WrappedEntity<T> entity) {

        if(!(entity instanceof VisibleEntity)){
            throw new IllegalArgumentException("Entities of the visible entity layer must be VisibleEntities, " +
                    "got passed: " + entity.getClass().getSimpleName());
        }

        super.add(entity);
    }

    public void setAllVisible(boolean allVisible){
        for (WrappedEntity<T> wrappedEntity : entities.values()) {
            if(wrappedEntity instanceof VisibleEntity){
                ((VisibleEntity) wrappedEntity).isVisibleProperty().set(allVisible);
            }
        }
    }

}
