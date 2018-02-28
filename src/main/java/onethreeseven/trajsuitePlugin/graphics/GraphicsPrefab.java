package onethreeseven.trajsuitePlugin.graphics;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.awt.*;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
public class GraphicsPrefab {

    public final ObjectProperty<double[]> centerLatLon;
    public final ObjectProperty<Color> color;

    public GraphicsPrefab(double[] latlon){
        this.centerLatLon = new SimpleObjectProperty<>(latlon);
        this.color = new SimpleObjectProperty<>(Color.BLUE);
    }

}
