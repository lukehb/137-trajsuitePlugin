package onethreeseven.trajsuitePlugin.view;


import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.util.Callback;
import onethreeseven.trajsuitePlugin.model.*;
import onethreeseven.trajsuitePlugin.transaction.AddEntitiesTransaction;
import onethreeseven.trajsuitePlugin.transaction.AddEntityUnit;
import onethreeseven.trajsuitePlugin.transaction.RemoveEntitiesTransaction;
import onethreeseven.trajsuitePlugin.transaction.RemoveEntityUnit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * A tree view that shows the entities in a {@link Layers} instance.
 * @author Luke Bermingham
 */
class EntityTreeView extends TreeView<ItemStateWidget> {

    private final Layers layers;

    EntityTreeView(Layers layers){
        super(new TreeItem<>());
        this.layers = layers;
        this.setShowRoot(false);
        getRoot().setExpanded(true);

        this.setCellFactory(makeCellFactory());

        remakeTree();
        //when entities added rebuild the tree
        this.layers.addEntitiesTransactionProperty.addListener(
                (observable, oldValue, newValue) -> onEntitiesAdded(newValue));
        //when entities removed rebuild the tree
        this.layers.removeEntitiesTransactionProperty.addListener(
                (observable, oldValue, newValue) -> onEntitiesRemoved(newValue));


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

    private TreeItem<ItemStateWidget> makeEntityTreeItem(String layername, WrappedEntity entity){

        if(entity instanceof VisibleEntity){
            return new TreeItem<>(new VisibleEntityStateWidget(layername, (VisibleEntity) entity));
        }

        EntityStateWidget widget = new EntityStateWidget(layername, entity);
        return new TreeItem<>(widget);
    }

    private TreeItem<ItemStateWidget> makeLayerTreeItem(WrappedEntityLayer layer){

        if(layer instanceof VisibleEntityLayer){
            return new TreeItem<>(new VisibleLayerStateWidget((VisibleEntityLayer) layer));
        }

        LayerStateWidget widget = new LayerStateWidget(layer);
        return new TreeItem<>(widget);
    }

    /**
     * @return A map of layer-names in the layer tree
     */
    private Map<String, TreeItem<ItemStateWidget>> makeTreeLayerMap(){
        Map<String, TreeItem<ItemStateWidget>> existingLayerItems = new HashMap<>();

        for (TreeItem<ItemStateWidget> layerItem : getRoot().getChildren()) {
            ItemStateWidget widget = layerItem.getValue();
            if(widget instanceof LayerStateWidget){
                existingLayerItems.put(((LayerStateWidget) widget).layer.getLayerName(), layerItem);
            }
        }
        return existingLayerItems;
    }

    private void onEntitiesAdded(AddEntitiesTransaction transaction){
        Platform.runLater(()->{

            Map<String, TreeItem<ItemStateWidget>> existingLayerItems = makeTreeLayerMap();

            //go through each add entity unit and add a tree item if we haven't go one already
            for (AddEntityUnit addEntityUnit : transaction.getData()) {

                String layername = addEntityUnit.getLayername();
                WrappedEntityLayer layer = layers.getLayer(layername);
                WrappedEntity entity = layers.getEntity(layername, addEntityUnit.getEntityId());

                TreeItem<ItemStateWidget> existingLayerItem = existingLayerItems.get(layername);
                //layer does not exist
                if(existingLayerItem == null){
                    existingLayerItem = addLayerTreeItem(layer);
                }
                //make tree item for entity
                addEntityTreeItem(layername, existingLayerItem, entity);
                existingLayerItem.setExpanded(true);
            }

        });
    }

    private void onEntitiesRemoved(RemoveEntitiesTransaction transaction){

        Platform.runLater(()->{

            Map<String, TreeItem<ItemStateWidget>> existingLayerItems = makeTreeLayerMap();

            //go through each removal unit and try to look for a match
            //in the existing layer items in the tree view

            for (RemoveEntityUnit removeEntityUnit : transaction.getData()) {

                TreeItem<ItemStateWidget> existingLayerItem = existingLayerItems.get(removeEntityUnit.getLayername());
                if(existingLayerItem != null){

                    Iterator<TreeItem<ItemStateWidget>> iter = existingLayerItem.getChildren().iterator();
                    while(iter.hasNext()){
                        TreeItem<ItemStateWidget> entityTreeItem = iter.next();
                        ItemStateWidget widget = entityTreeItem.getValue();
                        //if the entity widget it the one in the remove transaction remove it
                        if(widget instanceof EntityStateWidget){
                            if(((EntityStateWidget) widget).entity.getId().equals(removeEntityUnit.getId())){
                                iter.remove();
                                //unbind any property bindings on removal
                                widget.unbind();
                            }
                        }

                    }

                    //if we have emptied the layer, remove it from the root too
                    if(existingLayerItem.getChildren().isEmpty()){
                        getRoot().getChildren().remove(existingLayerItem);
                        //unbind on removal
                        existingLayerItem.getValue().unbind();
                    }

                }

            }

        });

    }

    private TreeItem<ItemStateWidget> addLayerTreeItem(WrappedEntityLayer layer){
        TreeItem<ItemStateWidget> layerItem = makeLayerTreeItem(layer);
        getRoot().getChildren().add(layerItem);
        return layerItem;
    }

    private void addEntityTreeItem(String layername, TreeItem<ItemStateWidget> layerItem, WrappedEntity entity){
        for (TreeItem<ItemStateWidget> entityWidget : layerItem.getChildren()) {
            ItemStateWidget widget = entityWidget.getValue();
            if(widget instanceof EntityStateWidget){
                //same id found already
                if(((EntityStateWidget) widget).entity.getId().equals(entity.getId())){
                    return;
                }
            }
        }
        layerItem.getChildren().add(makeEntityTreeItem(layername, entity));
    }

    private void remakeTree(){

        Platform.runLater(()->{

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
                //accumulate layers to tree
                TreeItem<ItemStateWidget> layerItem = makeLayerTreeItem(layer);
                getRoot().getChildren().add(layerItem);
                layerItem.setExpanded(true);
                //accumulate each entity in that layer to the tree too
                for (WrappedEntity entity : layer) {
                    TreeItem<ItemStateWidget> entityItem = makeEntityTreeItem(layer.getLayerName(), entity);
                    layerItem.getChildren().add(entityItem);
                }
            }

        });
    }



}
