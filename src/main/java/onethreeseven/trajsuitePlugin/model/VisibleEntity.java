package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 * A {@link WrappedEntity} that can also toggle its visibility.
 * @author Luke Bermingham
 */
public class VisibleEntity<T> extends WrappedEntity<T> implements Visible {

    private final BooleanProperty isVisible = new SimpleBooleanProperty(true);

    public VisibleEntity(T model) {
        super(model);
    }

    public VisibleEntity(String id, T model) {
        super(id, model);
    }


    @Override
    public BooleanProperty isVisibleProperty() {
        return isVisible;
    }
}
