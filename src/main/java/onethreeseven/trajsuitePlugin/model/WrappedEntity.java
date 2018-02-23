package onethreeseven.trajsuitePlugin.model;


import onethreeseven.trajsuitePlugin.util.IdGenerator;

/**
 * Wrap some object as a selectable entity.
 * @author Luke Bermingham
 */
public class WrappedEntity<T> implements Entity, Selectable {

    protected final T model;
    private final String id;
    private boolean selected = false;

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
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public String toString() {
        return "id:" + getId() + "[" + model.toString() + "]";
    }
}
