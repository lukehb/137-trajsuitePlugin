package onethreeseven.trajsuitePlugin.graphics;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.awt.*;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
public interface GraphicsPayload {

    PackedVertexData createVertexData();

    RenderingModes getRenderingMode();

    BooleanProperty getIsDirtyProperty();

    BooleanProperty getDrawOnTopProperty();

    default ObjectProperty<Color> getFallbackColorProperty(){
        return new SimpleObjectProperty<>(Color.RED);
    }

}
