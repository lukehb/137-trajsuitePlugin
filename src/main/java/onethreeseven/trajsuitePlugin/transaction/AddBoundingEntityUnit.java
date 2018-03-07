package onethreeseven.trajsuitePlugin.transaction;

import onethreeseven.trajsuitePlugin.graphics.GraphicsPayload;
import onethreeseven.trajsuitePlugin.model.BoundingCoordinates;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
public class AddBoundingEntityUnit extends AddEntityUnit {

    protected final GraphicsPayload payload;

    AddBoundingEntityUnit(String layername, String id, BoundingCoordinates model, GraphicsPayload payload) {
        super(layername, id, model);
        this.payload = payload;
    }

    public GraphicsPayload getPayload() {
        return payload;
    }
}
