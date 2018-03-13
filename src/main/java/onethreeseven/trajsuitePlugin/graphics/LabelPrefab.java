package onethreeseven.trajsuitePlugin.graphics;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * For having a string hover in the world.
 * @author Luke Bermingham
 */
public class LabelPrefab extends GraphicsPrefab {

    public final boolean isAnnotation;
    public final StringProperty label;
    public final BooleanProperty doesScale;

    public LabelPrefab(String label, double[] latlon) {
        this(label, true, latlon);
    }

    public LabelPrefab(String label, boolean isAnnotation, double[] latlon){
        super(latlon);
        this.label = new SimpleStringProperty(label);
        this.isAnnotation = isAnnotation;
        this.doesScale = new SimpleBooleanProperty(false);
    }

}
