package onethreeseven.trajsuitePlugin.model;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import onethreeseven.trajsuitePlugin.util.IdGenerator;

/**
 * Wrap some object as a selectable entity.
 * @author Luke Bermingham
 */
public class WrappedEntity<T> implements Entity, Selectable {


    protected final T model;
    private final SimpleBooleanProperty isSelected = new SimpleBooleanProperty(false);
    private final String id;

    public WrappedEntity(T model){
        this(IdGenerator.nextId(), model);
    }

    public WrappedEntity(String id, T model){
        this.id = id;
        this.model = model;
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
}
