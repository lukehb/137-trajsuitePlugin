package onethreeseven.trajsuitePlugin.graphics;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
public class CirclePrefab extends GraphicsPrefab {

    public final DoubleProperty radiusMetres;

    public CirclePrefab(double[] latlon, double radiusMetres){
        super(latlon);
        this.radiusMetres = new SimpleDoubleProperty(radiusMetres);
    }

}
