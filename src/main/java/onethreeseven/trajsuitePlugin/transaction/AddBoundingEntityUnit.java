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
    protected final boolean visible;

    AddBoundingEntityUnit(String layername, String id, BoundingCoordinates model, GraphicsPayload payload) {
        this(layername, id, model, false, true, payload);
    }

    AddBoundingEntityUnit(String layername, String id, BoundingCoordinates model, boolean selected, boolean visible, GraphicsPayload payload){
        super(layername, id, model, selected);
        this.visible = visible;
        this.payload = payload;
    }

    public boolean isVisible() {
        return visible;
    }

    public GraphicsPayload getPayload() {
        return payload;
    }
}
