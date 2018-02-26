package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.ReadOnlyBooleanProperty;

/**
 * Whether or not the thing is selected.
 * @author Luke Bermingham
 */
public interface Selectable {

    ReadOnlyBooleanProperty isSelectedProperty();

}
