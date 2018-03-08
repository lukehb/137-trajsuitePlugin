package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.*;
import onethreeseven.trajsuitePlugin.view.AnyMatchingBooleanBinding;

import java.util.HashMap;
import java.util.Map;

/**
 * A {@link WrappedEntityLayer} that can toggle its visibility.
 * Note, toggling visibility effects all children, and likewise children can effect
 * the layers visibility (e.g. if they are all off the layer turns off).
 * @author Luke Bermingham
 */
public class VisibleEntityLayer extends WrappedEntityLayer implements Visible {

    private final ReadOnlyBooleanWrapper isVisible;

    public VisibleEntityLayer(String layername, Map<String, ? extends VisibleEntity> entities, boolean isVisible){
        super(layername, entities);
        this.isVisible = new ReadOnlyBooleanWrapper(isVisible);

        //setup binding so layer selection is always determined by children
        this.isVisible.bind(new AnyMatchingBooleanBinding<>(true, super.entities, tWrappedEntity -> {
            if(tWrappedEntity instanceof VisibleEntity){
                return ((VisibleEntity) tWrappedEntity).isVisibleProperty();
            }
            return null;
        }));
    }

    public VisibleEntityLayer(String layerName, boolean isVisible) {
        this(layerName, new HashMap<>(), isVisible);
    }

    @Override
    public ReadOnlyBooleanProperty isVisibleProperty() {
        return isVisible.getReadOnlyProperty();
    }

    @Override
    public void add(WrappedEntity entity) {

        if(!(entity instanceof VisibleEntity)){
            throw new IllegalArgumentException("Entities of the visible entity layer must be VisibleEntities, " +
                    "got passed: " + entity.getClass().getSimpleName());
        }

        super.add(entity);
    }

    public void setAllVisible(boolean allVisible){
        for (WrappedEntity wrappedEntity : entities.values()) {
            if(wrappedEntity instanceof VisibleEntity){
                ((VisibleEntity) wrappedEntity).isVisibleProperty().set(allVisible);
            }
        }
    }

}
