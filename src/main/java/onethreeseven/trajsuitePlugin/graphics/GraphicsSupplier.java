package onethreeseven.trajsuitePlugin.graphics;

import onethreeseven.trajsuitePlugin.model.BoundingCoordinates;

/**
 * If your plugin need to render an entity that gets added to the layers
 * @author Luke Bermingham
 */
public interface GraphicsSupplier {

    <T extends BoundingCoordinates> GraphicsPayload supply(T modelType);

}
