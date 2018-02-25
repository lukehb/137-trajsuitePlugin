package onethreeseven.trajsuitePlugin.view;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import onethreeseven.trajsuitePlugin.model.Layers;
import onethreeseven.trajsuitePlugin.model.WrappedEntity;
import onethreeseven.trajsuitePlugin.model.WrappedEntityLayer;


/**
 * A tree view that shows the entities in a {@link Layers} instance.
 * @author Luke Bermingham
 */
public class EntityTreeView extends TreeView<WrappedEntity> {

    private final Layers layers;

    public EntityTreeView(Layers layers){
        super(new CheckBoxTreeItem<>());
        this.layers = layers;
        this.setShowRoot(false);
        getRoot().setExpanded(true);

        this.setCellFactory(makeCellFactory());

        remakeTree();
        //when layers change rebuild tree
        this.layers.entityChangedProperty().addListener((observable, oldValue, newValue) -> remakeTree());
    }

    private Callback<TreeView<WrappedEntity>, TreeCell<WrappedEntity>> makeCellFactory(){

        StringConverter<TreeItem<WrappedEntity>> converter =
                new StringConverter<>() {
                    @Override
                    public String toString(TreeItem<WrappedEntity> treeItem) {
                        return (treeItem == null || treeItem.getValue() == null) ?
                                "" : treeItem.getValue().toString();
                    }

                    @Override
                    public TreeItem<WrappedEntity> fromString(String string) {
                        return new TreeItem<>(new WrappedEntity<>(string));
                    }
                };

        Callback<TreeItem<WrappedEntity>, ObservableValue<Boolean>> getSelectedProperty =
                item -> {
                    if (item instanceof CheckBoxTreeItem<?>) {
                        return ((CheckBoxTreeItem<?>)item).selectedProperty();
                    }
                    return null;
                };


        return param -> {

            CheckBoxTreeCell<WrappedEntity> cell =
                    new CheckBoxTreeCell<>(getSelectedProperty, converter);

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

    private CheckBoxTreeItem<WrappedEntity> makeItem(WrappedEntity entity){
        return new CheckBoxTreeItem<>(entity, null, entity.isSelectedProperty().getValue(), false);
    }

    private void remakeTree(){
        getRoot().getChildren().clear();
        for (WrappedEntityLayer layer : layers) {
            //to put layers in the tree-view we must give them a mock entity
            TreeItem<WrappedEntity> layerItem = makeItem(new LayerAsWrappedEntity(layer));
            getRoot().getChildren().add(layerItem);
            layerItem.setExpanded(true);
            //add entities in that layer
            for (Object entity : layer) {
                if(entity instanceof WrappedEntity){
                    CheckBoxTreeItem<WrappedEntity> entityItem = makeItem((WrappedEntity) entity);
                    //bind selection of entity to selection of checkbox and vice-versa
                    //this implicitly handles layers too
                    ((WrappedEntity) entity).isSelectedProperty().bindBidirectional(entityItem.selectedProperty());
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

}
