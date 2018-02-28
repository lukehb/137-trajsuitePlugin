package onethreeseven.trajsuitePlugin.graphics;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import onethreeseven.geo.model.LatLonBounds;
import onethreeseven.trajsuitePlugin.model.BoundingCoordinates;

import java.awt.*;
import java.util.Arrays;

/**
 * Base class to extend when making your own graphics payloads.
 * @author Luke Bermingham
 */
public abstract class GraphicsPayload {

    public final BooleanProperty isDirty;
    public final BooleanProperty drawOnTop;
    public final BooleanProperty doScalePointsOrLines;
    public final IntegerProperty pointOrLineSize;
    public final BooleanProperty smoothPoints;
    public final ObjectProperty<Color> fallbackColor;
    public final ObservableList<GraphicsPrefab> additionalPrefabs;

    /**
     * The string property for annotation text. An empty string means have no annotation, a non-empty string means have an annotation.
     */
    public final StringProperty annotationText;

    private RenderingModes renderingMode;
    private final RenderingModes[] acceptedRenderingModes;

    public GraphicsPayload(){
        this.isDirty = new SimpleBooleanProperty(true);
        this.drawOnTop = new SimpleBooleanProperty(false);
        this.doScalePointsOrLines = new SimpleBooleanProperty(true);
        this.pointOrLineSize = new SimpleIntegerProperty(3);
        this.smoothPoints = new SimpleBooleanProperty(true);
        this.fallbackColor = new SimpleObjectProperty<>(Color.RED);
        this.renderingMode = defaultRenderingMode();
        this.acceptedRenderingModes = getAcceptedRenderingModes();
        this.annotationText = new SimpleStringProperty("");
        this.additionalPrefabs = FXCollections.observableArrayList();
    }

    protected abstract RenderingModes defaultRenderingMode();

    protected RenderingModes[] getAcceptedRenderingModes(){
        return new RenderingModes[]{defaultRenderingMode()};
    }

    public RenderingModes getRenderingMode(){
        return this.renderingMode;
    }

    public void setRenderingMode(RenderingModes newMode){
        for (RenderingModes acceptedRenderingMode : acceptedRenderingModes) {
            if(acceptedRenderingMode == newMode){
                this.renderingMode = newMode;
                break;
            }
        }
        throw new IllegalArgumentException("The render mode you passed: "
                + newMode +
                " was not one of the accetped rendering modes: " +
                Arrays.toString(acceptedRenderingModes));
    }



    /**
     * If true, then {@link GraphicsPayload#onViewportChanged(LatLonBounds)} is called every time
     * the viewport changes. It is unlikely your graphic will need this to be true. So by default it is false.
     * @return True if your graphic needs and wants to be sent updates regarding
     * viewport changes.
     */
    public boolean wantsViewportBoundsUpdates(){return false;}

    /**
     * If {@link GraphicsPayload#wantsViewportBoundsUpdates()} returns true this is called
     * every time the viewport changes. It is unlikely your graphic will need such information
     * so by default this method is a no-op.
     * @param viewportBounds The geographic bounds of the view port.
     */
    public void onViewportChanged(LatLonBounds viewportBounds){}

    /**
     * Create packed vertex data used to draw the passed model in the set rendering mode,
     * @param model The model to make vertex data for.
     * @return Packed vertex data.
     */
    public abstract PackedVertexData createVertexData(BoundingCoordinates model);

}
