package onethreeseven.trajsuitePlugin.view;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Labeled;
import javafx.scene.control.skin.ButtonSkin;
import javafx.scene.control.skin.LabelSkin;
import javafx.scene.control.skin.LabeledSkinBase;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import onethreeseven.trajsuitePlugin.model.Visible;


/**
 * Shows a visible or invisible widget/icon depending on the state of
 * its "isVisible" property.
 * @author Luke Bermingham
 */
public class VisibilityWidget extends Labeled {

    private static final String visibleSVG = "M11.5,5a11.78,11.78,0,0,0-11,7.5,11.82,11.82,0,0,0,22,0A11.78,11.78,0,0,0,11.5,5Zm0,12.5a5,5,0,1,1,5-5A5,5,0,0,1,11.5,17.5Zm0-8a3,3,0,1,0,3,3A2.95,2.95,0,0,0,11.5,9.5Z";

    private static final String invisibleSVG = "M12,7a5,5,0,0,1,5,5,4.85,4.85,0,0,1-.36,1.83l2.92,2.92A11.82,11.82,0,0,0,23,12,11.83,11.83,0,0,0,12,4.5a11.65,11.65,0,0,0-4,.7l2.16,2.16A4.85,4.85,0,0,1,12,7ZM4,6.27L4.74,7A11.8,11.8,0,0,0,1,12a11.82,11.82,0,0,0,15.38,6.66l0.42,0.42,0.72,0.73,1.27-1.27L5.2,5ZM7.53,9.8l1.55,1.55A2.82,2.82,0,0,0,9,12a3,3,0,0,0,3,3,2.82,2.82,0,0,0,.65-0.08l1.55,1.55A5,5,0,0,1,7.53,9.8ZM11.84,9L15,12.17,15,12a3,3,0,0,0-3-3H11.84Z";

    private final BooleanProperty isVisible;
    private final SVGPath visibleSVGPath;
    private final SVGPath invisibleSVGPath;


    public VisibilityWidget(boolean initialVisibility){
        super();
        this.isVisible = new SimpleBooleanProperty(initialVisibility);

        //visible icon
        this.visibleSVGPath = new SVGPath();
        this.visibleSVGPath.setContent(visibleSVG);
        this.visibleSVGPath.setFill(Color.BLACK);

        //invisible icon
        this.invisibleSVGPath = new SVGPath();
        this.invisibleSVGPath.setContent(invisibleSVG);
        this.invisibleSVGPath.setFill(Color.BLACK);

        //change button background visibility

        //set graphic initially
        refreshGraphic();

        //change state on button press
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            VisibilityWidget.this.isVisible.set(!VisibilityWidget.this.isVisible.get());
            refreshGraphic();
        });

        //update graphic when visibility property changes
        this.isVisibleProperty().addListener((observable, oldValue, newValue) -> refreshGraphic());

        setSkin(new LabeledSkinBase<>(this) {

        });



    }

    protected void refreshGraphic(){
        if(isVisible.get()){
            this.setGraphic(visibleSVGPath);
        }else{
            this.setGraphic(invisibleSVGPath);
        }
    }

    public BooleanProperty isVisibleProperty() {
        return isVisible;
    }
}
