package onethreeseven.trajsuitePlugin.transaction;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
public class RemoveEntityUnit implements TransactionUnit {

    protected final String layername;
    protected final String id;

    RemoveEntityUnit(String layername, String entityId) {
        this.layername = layername;
        this.id = entityId;
    }

    public String getLayername() {
        return layername;
    }

    public String getId() {
        return id;
    }
}
