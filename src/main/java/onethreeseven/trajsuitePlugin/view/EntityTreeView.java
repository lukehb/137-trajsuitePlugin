package onethreeseven.trajsuitePlugin.view;


import javafx.scene.control.*;
import javafx.util.Callback;
import onethreeseven.trajsuitePlugin.model.*;


/**
 * A tree view that shows the entities in a {@link Layers} instance.
 * @author Luke Bermingham
 */
public class EntityTreeView extends TreeView<ItemStateWidget> {

    private final Layers layers;

    public EntityTreeView(Layers layers){
        super(new TreeItem<>());
        this.layers = layers;
        this.setShowRoot(false);
        getRoot().setExpanded(true);

        this.setCellFactory(makeCellFactory());

        remakeTree();
        //when layers change rebuild tree
        this.layers.addEntityChangedListener(this::remakeTree);


    }

    private Callback<TreeView<ItemStateWidget>, TreeCell<ItemStateWidget>> makeCellFactory(){

        return param -> {

            final ItemStateWidgetTreeCell cell = new ItemStateWidgetTreeCell();
            final ContextMenu menu = new ContextMenu();

            //setup delete right click option
            MenuItem deleteMenuItem = new MenuItem("Delete");
            deleteMenuItem.setOnAction(event -> {
                ItemStateWidget cellValue = cell.getItem();
                cellValue.removeFromLayers(layers);
                //cleanup attached menus too
                menu.getItems().clear();
                cell.setContextMenu(null);
            });

            //attach menus
            menu.getItems().add(deleteMenuItem);
            cell.setContextMenu(menu);

            return cell;
        };

    }

    private TreeItem<ItemStateWidget> makeEntityTreeItem(WrappedEntity entity){

        if(entity instanceof VisibleEntity){
            return new TreeItem<>(new VisibleEntityStateWidget((VisibleEntity) entity));
        }

        EntityStateWidget widget = new EntityStateWidget(entity);
        return new TreeItem<>(widget);
    }

    private TreeItem<ItemStateWidget> makeLayerTreeItem(WrappedEntityLayer layer){

        if(layer instanceof VisibleEntityLayer){
            return new TreeItem<>(new VisibleLayerStateWidget((VisibleEntityLayer) layer));
        }

        LayerStateWidget widget = new LayerStateWidget(layer);
        return new TreeItem<>(widget);
    }

    private void remakeTree(){

        //unbind all widgets we have
        for (TreeItem<ItemStateWidget> layerTreeItems : getRoot().getChildren()) {
            layerTreeItems.getValue().unbind();
            for (TreeItem<ItemStateWidget> entityTreeItems : layerTreeItems.getChildren()) {
                entityTreeItems.getValue().unbind();
            }
        }

        //clear the tree
        getRoot().getChildren().clear();

        for (WrappedEntityLayer layer : layers) {
            //add layers to tree
            TreeItem<ItemStateWidget> layerItem = makeLayerTreeItem(layer);
            getRoot().getChildren().add(layerItem);
            layerItem.setExpanded(true);
            //add each entity in that layer to the tree too
            for (Object entity : layer) {
                if(entity instanceof WrappedEntity){
                    TreeItem<ItemStateWidget> entityItem = makeEntityTreeItem((WrappedEntity) entity);
                    layerItem.getChildren().add(entityItem);
                }
            }
        }
    }



}
