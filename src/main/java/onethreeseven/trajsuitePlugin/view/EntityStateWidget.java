package onethreeseven.trajsuitePlugin.view;

import javafx.scene.control.CheckBox;
import onethreeseven.trajsuitePlugin.model.Layers;
import onethreeseven.trajsuitePlugin.model.Selectable;
import onethreeseven.trajsuitePlugin.model.WrappedEntity;

/**
 * A widget that represents entity selection state with a checkbox.
 * When the checkbox is clicked the selection state of the entity changes and vice-versa.
 * @author Luke Bermingham
 */
public class EntityStateWidget extends ItemStateWidget {

    protected final WrappedEntity entity;

    //this tracks the entity
    protected final CheckBox selectionWidget;

    protected EntityStateWidget(WrappedEntity entity){
        this.entity = entity;
        this.selectionWidget = new CheckBox();
        this.selectionWidget.setSelected(entity.isSelectedProperty().get());
        //add checkbox to this node (a hbox)
        this.getChildren().add(this.selectionWidget);
        //bi-directional bind the selection property of the checkbox to the entity
        this.selectionWidget.selectedProperty().bindBidirectional(entity.isSelectedProperty());
    }

    @Override
    public Selectable getSelectable() {
        return entity;
    }

    @Override
    public void removeFromLayers(Layers layers) {
        layers.remove(entity.getId(), entity.getModel().getClass());
    }

    @Override
    public void unbind() {
        this.selectionWidget.selectedProperty().unbindBidirectional(entity.isSelectedProperty());
    }

    @Override
    public String itemAsText() {
        return entity.toString();
    }
}
