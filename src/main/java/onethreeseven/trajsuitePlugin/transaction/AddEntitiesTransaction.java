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

    public AddEntitiesTransaction add(String layername, String entityId, Object model, boolean selected){
        super.add(new AddEntityUnit(layername, entityId, model, selected));
        return this;
    }

    public AddEntitiesTransaction add(String layername, String entityId, BoundingCoordinates model, GraphicsPayload payload){
        super.add(new AddBoundingEntityUnit(layername, entityId, model, payload));
        return this;
    }

    public AddEntitiesTransaction add(String layername, String entityId, BoundingCoordinates model, boolean selected, boolean visible, GraphicsPayload payload){
        super.add(new AddBoundingEntityUnit(layername, entityId, model, selected, visible, payload));
        return this;
    }

}
