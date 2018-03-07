package onethreeseven.trajsuitePlugin.view;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import onethreeseven.trajsuitePlugin.model.VisibleEntityLayer;

/**
 * All the functionality of {@link LayerStateWidget} but with an extra widget that
 * represents and toggles the visibility of the layer.
 * @author Luke Bermingham
 */
public class VisibleLayerStateWidget extends LayerStateWidget {

    private final VisibilityWidget visibilityWidget;

    private final ChangeListener<? super Boolean> layerVisibilityChangeListener;
    private final EventHandler<? super MouseEvent> visibilityWidgetClickedHandler;

    protected VisibleLayerStateWidget(VisibleEntityLayer layer) {
        super(layer);
        this.visibilityWidget = new VisibilityWidget(layer.isVisibleProperty().get());

        //have to use listeners here because layer selection property is already bound

        //update checkbox when layer is selected
        layerVisibilityChangeListener = (ChangeListener<Boolean>) (observable, oldValue, newValue) ->
                visibilityWidget.isVisibleProperty().set(newValue);
        layer.isVisibleProperty().addListener(layerVisibilityChangeListener);

        //when checkbox is clicked toggle all children selections
        visibilityWidgetClickedHandler = (EventHandler<MouseEvent>) event -> {
            layer.setAllVisible(visibilityWidget.isVisibleProperty().get());
        };
        visibilityWidget.addEventHandler(MouseEvent.MOUSE_CLICKED, visibilityWidgetClickedHandler);

        //accumulate the checkbox to the hox
        getChildren().add(visibilityWidget);

    }

    @Override
    public void unbind() {
        super.unbind();
        if(layer instanceof VisibleEntityLayer){
            ((VisibleEntityLayer) layer).isVisibleProperty().removeListener(layerVisibilityChangeListener);
        }
        visibilityWidget.removeEventHandler(MouseEvent.MOUSE_CLICKED, visibilityWidgetClickedHandler);
    }
}
