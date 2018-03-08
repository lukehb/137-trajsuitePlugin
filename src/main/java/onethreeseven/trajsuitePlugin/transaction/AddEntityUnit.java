package onethreeseven.trajsuitePlugin.transaction;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
public class AddEntityUnit implements TransactionUnit {

    protected final String layername;
    protected final String entityId;
    protected final Object model;
    protected final boolean selected;

    AddEntityUnit(String layername, String entityId, Object model) {
        this.layername = layername;
        this.entityId = entityId;
        this.model = model;
        this.selected = false;
    }

    public AddEntityUnit(String layername, String entityId, Object model, boolean selected) {
        this.layername = layername;
        this.entityId = entityId;
        this.model = model;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public String getLayername() {
        return layername;
    }

    public String getEntityId() {
        return entityId;
    }

    public Object getModel() {
        return model;
    }
}
