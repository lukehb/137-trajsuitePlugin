package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.BooleanProperty;

/**
 * Entities/layers that need their visibility toggled should implement this.
 * @author Luke Bermingham
 */
public interface Visible {

    BooleanProperty isVisibleProperty();

}
