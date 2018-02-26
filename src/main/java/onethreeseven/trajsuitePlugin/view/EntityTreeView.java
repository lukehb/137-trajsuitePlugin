package onethreeseven.trajsuitePlugin.view;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.util.Callback;
import javafx.util.StringConverter;
import onethreeseven.trajsuitePlugin.model.*;


/**
 * A tree view that shows the entities in a {@link Layers} instance.
 * @author Luke Bermingham
 */
public class EntityTreeView extends TreeView<WrappedEntity> {

    private final Layers layers;

    public EntityTreeView(Layers layers){
        super(new TreeItem<>());
        this.layers = layers;
        this.setShowRoot(false);
        getRoot().setExpanded(true);

        setBackground(Background.EMPTY);

        this.setCellFactory(makeCellFactory());

        remakeTree();
        //when layers change rebuild tree
        this.layers.entityChangedProperty().addListener((observable, oldValue, newValue) -> remakeTree());


    }

    private Callback<TreeView<WrappedEntity>, TreeCell<WrappedEntity>> makeCellFactory(){

        return param -> {

            LukeTreeCell cell = new LukeTreeCell();

            ContextMenu menu = new ContextMenu();

            //setup delete right click option
            MenuItem deleteMenuItem = new MenuItem("Delete");
            deleteMenuItem.setOnAction(event -> {
                WrappedEntity cellValue = cell.getItem();
                if(cellValue instanceof LayerAsWrappedEntity){
                    layers.removeLayer(((LayerAsWrappedEntity)cellValue).getModel().getModelType());
                }
                else{
                    layers.remove(cell.getItem());
                }

            });

            menu.getItems().add(deleteMenuItem);

            cell.setContextMenu(menu);
            return cell;
        };

    }

    private TreeItem<WrappedEntity> makeItem(WrappedEntity entity){

        if(entity instanceof Visible){

            VisibilityWidget visibilityWidget = new VisibilityWidget(((Visible) entity).isVisibleProperty().get());
            return new TreeItem<>(entity, visibilityWidget);
        }

        //non-visible entity
        return new TreeItem<>(entity);

    }

    private TreeItem<WrappedEntity> makeLayerTreeItem(WrappedEntityLayer layer){

        TreeItem<WrappedEntity> treeItem;

        if(layer instanceof VisibleEntityLayer){
            final TreeItem<WrappedEntity> layerItem = makeItem(new VisibleLayerAsWrappedEntity((VisibleEntityLayer) layer));
            //update children
            Node graphic = layerItem.getGraphic();
            if(graphic instanceof VisibilityWidget){

                //special case for visible layer, the layer drives the graphic instead of binding
                ((VisibleEntityLayer) layer).isVisibleProperty().addListener(new WeakChangeListener<>(
                        (observable, oldValue, newValue) -> ((VisibilityWidget) graphic).isVisibleProperty().set(newValue)));

                //when graphic is specifically clicked, (not just an update from a property) update all the children

                graphic.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                    for (TreeItem<WrappedEntity> childTreeItem : layerItem.getChildren()) {
                        WrappedEntity childItem = childTreeItem.getValue();
                        if(childItem instanceof VisibleEntity){
                            ((VisibleEntity) childItem).isVisibleProperty().set(((VisibilityWidget) graphic).isVisibleProperty().get());
                        }
                    }
                });

            }
            treeItem = layerItem;
        }
        else{
            treeItem = makeItem(new LayerAsWrappedEntity(layer));
        }

        return treeItem;
    }

    private TreeItem<WrappedEntity> makeEntityTreeItem(WrappedEntity entity){
        TreeItem<WrappedEntity> entityItem = makeItem(entity);
        //bind selection of entity to selection of graphic and vice-versa
        //this implicitly handles layers too
        //entity.isSelectedProperty().bindBidirectional(entityItem.selectedProperty());

        //if entity is visible bind the visibility to the visibility widget
        if(entity instanceof VisibleEntity){
            Node graphicNode = entityItem.getGraphic();
            if(graphicNode instanceof VisibilityWidget){
                ((VisibleEntity) entity).isVisibleProperty().bindBidirectional(((VisibilityWidget) graphicNode).isVisibleProperty());
            }
        }

        return entityItem;
    }

    private void remakeTree(){

        //unbind
        for (TreeItem<WrappedEntity> layerTreeItem : getRoot().getChildren()) {
            for (TreeItem<WrappedEntity> entityTreeItem : layerTreeItem.getChildren()) {
                Node graphicNode = entityTreeItem.getGraphic();
                WrappedEntity entity = entityTreeItem.getValue();
                //unbind visibility property on the graphic
                if(graphicNode instanceof VisibilityWidget && entity instanceof VisibleEntity){
                    ((VisibleEntity) entityTreeItem.getValue()).isVisibleProperty().unbindBidirectional(((VisibilityWidget) graphicNode).isVisibleProperty());
                }

                //unbind the selection property on the tree item
                //todo
            }
        }

        getRoot().getChildren().clear();

        for (WrappedEntityLayer layer : layers) {
            //add layers to tree
            TreeItem<WrappedEntity> layerItem = makeLayerTreeItem(layer);

            getRoot().getChildren().add(layerItem);
            layerItem.setExpanded(true);
            //add each entity in that layer to the tree too
            for (Object entity : layer) {
                if(entity instanceof WrappedEntity){
                    TreeItem<WrappedEntity> entityItem = makeEntityTreeItem((WrappedEntity) entity);
                    layerItem.getChildren().add(entityItem);
                }
            }
        }
    }

    private class LayerAsWrappedEntity extends WrappedEntity<WrappedEntityLayer> {
        public LayerAsWrappedEntity(WrappedEntityLayer model) {
            super(model);
        }

        @Override
        public String toString() {
            return "LAYER of " + model.getLayerName();
        }
    }

    private class VisibleLayerAsWrappedEntity extends LayerAsWrappedEntity implements Visible {

        public VisibleLayerAsWrappedEntity(VisibleEntityLayer model) {
            super(model);
        }

        @Override
        public ReadOnlyBooleanProperty isVisibleProperty() {
            if(model instanceof Visible){
                return ((Visible) model).isVisibleProperty();
            }
            return null;
        }
    }

}
