package onethreeseven.trajsuitePlugin.view;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import onethreeseven.trajsuitePlugin.model.Layers;
import onethreeseven.trajsuitePlugin.model.Selectable;
import onethreeseven.trajsuitePlugin.model.WrappedEntityLayer;



/**
 * A widget that represent the selection state of a layer using a checkbox
 * and propagates changes in that checkbox to children in the layer.
 * Likewise, changes to the layer state of layer selection are propagated to the checkbox.
 * @author Luke Bermingham
 */
public class LayerStateWidget extends ItemStateWidget {

    protected final WrappedEntityLayer layer;
    private final CheckBox checkBox;

    private final ChangeListener<? super Boolean> layerSelectedChangeListener;
    private final EventHandler<? super MouseEvent> checkboxClickedHandler;

    protected LayerStateWidget(WrappedEntityLayer layer) {
        this.layer = layer;
        this.checkBox = new CheckBox();
        this.checkBox.setSelected(layer.isSelectedProperty().get());

        //have to use listeners here because layer selection property is already bound

        //update checkbox when layer is selected
        layerSelectedChangeListener = (ChangeListener<Boolean>) (observable, oldValue, newValue) ->
                checkBox.setSelected(newValue);
        layer.isSelectedProperty().addListener(layerSelectedChangeListener);

        //when checkbox is clicked toggle all children selections
        checkboxClickedHandler = (EventHandler<MouseEvent>) event -> {
            layer.selectAll(checkBox.isSelected());
        };
        checkBox.addEventHandler(MouseEvent.MOUSE_CLICKED, checkboxClickedHandler);

        //add the checkbox to the hox
        getChildren().add(checkBox);

    }

    @Override
    public Selectable getSelectable() {
        return layer;
    }

    @Override
    public void removeFromLayers(Layers layers) {
        layers.removeLayer(layer.getModelType());
    }

    @Override
    public void unbind() {
        checkBox.removeEventHandler(MouseEvent.MOUSE_CLICKED, checkboxClickedHandler);
        layer.isSelectedProperty().removeListener(layerSelectedChangeListener);
    }

    @Override
    public String itemAsText() {
        return "LAYER of " + layer.getLayerName();
    }
}
