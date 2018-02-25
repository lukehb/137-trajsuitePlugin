package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * A {@link WrappedEntityLayer} that can toggle its visibility.
 * Note, toggling visibility effects all children, and likewise children can effect
 * the layers visibility (e.g. if they are all off the layer turns off).
 * @author Luke Bermingham
 */
public class VisibleEntityLayer<T> extends WrappedEntityLayer<T> implements Visible {

    private final SimpleBooleanProperty isVisible;

    public VisibleEntityLayer(String layerName, Class<T> modelType) {
        super(layerName, modelType);
        this.isVisible = new SimpleBooleanProperty(true);

        //setup selection change listener, so when layer changes, so do the children
        this.isVisible.addListener((observable, oldValue, newValue) -> {
            for (Entity entity : this) {
                if(entity instanceof VisibleEntity){
                    ((VisibleEntity) entity).isVisibleProperty().set(newValue);
                }
            }
        });

        //setup binding so layer selection is always determined by children
        this.isVisible.bind(new AllSameBooleanBinding<>(false, entities, tWrappedEntity -> {
            if(tWrappedEntity instanceof VisibleEntity){
                return ((VisibleEntity) tWrappedEntity).isVisibleProperty();
            }
            return null;
        }));
    }

    @Override
    public BooleanProperty isVisibleProperty() {
        return isVisible;
    }



}
