package onethreeseven.trajsuitePlugin.transaction;

import onethreeseven.trajsuitePlugin.graphics.GraphicsPayload;
import onethreeseven.trajsuitePlugin.model.BoundingCoordinates;

/**
 * Transaction for adding entities.
 * @author Luke Bermingham
 */
public class AddEntitiesTransaction extends EntityTransaction<AddEntityUnit> {

    public AddEntitiesTransaction(){
        super();
    }

    public AddEntitiesTransaction add(String layername, String entityId, Object model){
        super.add(new AddEntityUnit(layername, entityId, model));
        return this;
    }

    public AddEntitiesTransaction add(String layername, String entityId, BoundingCoordinates model, GraphicsPayload payload){
        super.add(new AddBoundingEntityUnit(layername, entityId, model, payload));
        return this;
    }

}
