package onethreeseven.trajsuitePlugin.graphics;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * For having a string hover in the world.
 * @author Luke Bermingham
 */
public class LabelPrefab extends GraphicsPrefab {

    public final StringProperty label;

    public LabelPrefab(String label, double[] latlon) {
        super(latlon);
        this.label = new SimpleStringProperty(label);
    }

}
