package onethreeseven.trajsuitePlugin.view;

import javafx.scene.layout.HBox;
import onethreeseven.trajsuitePlugin.model.Layers;
import onethreeseven.trajsuitePlugin.model.Selectable;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
public abstract class ItemStateWidget extends HBox {

    public abstract Selectable getSelectable();

    public abstract void unbind();

    public abstract String itemAsText();

    @Override
    public String toString() {
        return itemAsText();
    }
}
