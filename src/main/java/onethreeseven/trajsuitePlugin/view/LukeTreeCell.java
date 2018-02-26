package onethreeseven.trajsuitePlugin.view;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import onethreeseven.trajsuitePlugin.model.WrappedEntity;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
public class LukeTreeCell extends TreeCell<WrappedEntity> {

    @Override
    protected void updateItem(WrappedEntity item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {

            TreeItem<WrappedEntity> treeItem = getTreeItem();

            if(treeItem.getGraphic() != null){
                //todo: make a custom node that is a checkbox and visibility icon
                setGraphic(treeItem.getGraphic());
            }

            setText(item.toString());
        }


    }
}
