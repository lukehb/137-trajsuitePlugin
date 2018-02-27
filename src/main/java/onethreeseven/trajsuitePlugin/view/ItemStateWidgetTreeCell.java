package onethreeseven.trajsuitePlugin.view;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;

/**
 * A tree cell for representing {@link ItemStateWidget}
 * @author Luke Bermingham
 */
public class ItemStateWidgetTreeCell extends TreeCell<ItemStateWidget> {

    @Override
    protected void updateItem(ItemStateWidget item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {

            TreeItem<ItemStateWidget> treeItem = getTreeItem();

            if(treeItem.getValue() != null){
                setGraphic(treeItem.getValue());
            }

            setText(item.toString());
        }


    }
}
