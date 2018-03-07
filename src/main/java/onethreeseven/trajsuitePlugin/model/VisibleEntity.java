package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import onethreeseven.trajsuitePlugin.util.IdGenerator;

/**
 * A {@link WrappedEntity} that can also toggle its visibility.
 * @author Luke Bermingham
 */
public class VisibleEntity<T> extends WrappedEntity<T> implements Visible {

    private final SimpleBooleanProperty isVisible;

    public VisibleEntity(T model, boolean isVisible) {
        this(IdGenerator.nextId(), model, isVisible);
    }

    public VisibleEntity(String id, T model, boolean isVisible) {
        super(id, model);
        this.isVisible = new SimpleBooleanProperty(isVisible);

        //accumulate visibility as an anything change listener
        this.isVisible.addListener((observable, oldValue, newValue) -> fireAnythingChangedListeners());

    }

    @Override
    public BooleanProperty isVisibleProperty() {
        return isVisible;
    }


}
