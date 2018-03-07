package onethreeseven.trajsuitePlugin.model;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import onethreeseven.trajsuitePlugin.util.IdGenerator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Wrap some object as a selectable entity.
 * @author Luke Bermingham
 */
public class WrappedEntity<T> implements Entity, Selectable {

    protected final Collection<Runnable> anythingChangedListeners;

    protected final T model;
    private final SimpleBooleanProperty isSelected;
    private final String id;

    public WrappedEntity(T model){
        this(IdGenerator.nextId(), model);
    }

    public WrappedEntity(String id, T model){
        this.id = id;
        this.model = model;
        this.isSelected = new SimpleBooleanProperty(false);
        this.anythingChangedListeners = new ArrayList<>();

        //accumulate selection changed as a cause for firing off the listener
        this.isSelected.addListener((observable, oldValue, newValue) -> fireAnythingChangedListeners());

    }

    public T getModel(){
        return model;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "[" + getId() + "] " + model.toString();
    }

    @Override
    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public void addAnythingChangedListener(Runnable listener){
        this.anythingChangedListeners.add(listener);
    }

    public void removeAnythingChangedListener(Runnable listener){
        this.anythingChangedListeners.remove(listener);
    }

    protected void fireAnythingChangedListeners(){
        for (Runnable anythingChangedListener : anythingChangedListeners) {
            anythingChangedListener.run();
        }
    }

}
