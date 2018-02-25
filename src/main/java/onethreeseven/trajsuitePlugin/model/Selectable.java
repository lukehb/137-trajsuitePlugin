package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.BooleanProperty;

/**
 * Whether or not the thing is selected.
 * @author Luke Bermingham
 */
public interface Selectable {

    BooleanProperty isSelectedProperty();

}
