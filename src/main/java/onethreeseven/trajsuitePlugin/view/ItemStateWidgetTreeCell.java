package onethreeseven.trajsuitePlugin.view;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import java.util.ServiceLoader;

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
            this.setContextMenu(null);
        } else {

            TreeItem<ItemStateWidget> treeItem = getTreeItem();

            if(treeItem.getValue() != null){
                setGraphic(treeItem.getValue());
            }

            setText(item.toString());

            setupContextMenu();

        }


    }

    protected void setupContextMenu(){

        final ContextMenu menu = new ContextMenu();

        ContextMenuPopulator populator = new ContextMenuPopulator(menu);

        ItemStateWidget cellValue = this.getItem();

        ServiceLoader<EntityContextMenuSupplier> serviceLoader = ServiceLoader.load(EntityContextMenuSupplier.class);
        for (EntityContextMenuSupplier menuSupplier : serviceLoader) {

            if(cellValue instanceof EntityStateWidget){
                menuSupplier.supplyMenuForEntity(populator, ((EntityStateWidget) cellValue).entity, ((EntityStateWidget) cellValue).layername);
            }
            else if(cellValue instanceof LayerStateWidget) {
                menuSupplier.supplyMenuForLayer(populator, ((LayerStateWidget) cellValue).layer);
            }

        }

        this.setContextMenu(menu);

    }

}
