package onethreeseven.trajsuitePlugin.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import onethreeseven.trajsuitePlugin.model.Layers;

/**
 * Some utils for dealing with views
 * @author Luke Bermingham
 */
final class ViewUtil {

    private static Stage layerStage = null;

    public static void showEntityLayersWindow(Layers layers, Stage owner){
        Platform.runLater(()->{
            if(layerStage == null){
                layerStage = new Stage();
                layerStage.setX(0);

                final int prefWidth = 300;
                final int prefHeight = 600;

                //put layers in scroll pane
                ScrollPane layersViewParent = new ScrollPane();
                BorderPane contentParent = new BorderPane();
                contentParent.setPrefHeight(prefHeight);
                contentParent.setPrefWidth(prefWidth);
                contentParent.setCenter(new EntityTreeView(layers));
                layersViewParent.setContent(contentParent);

                layerStage.setScene(new Scene(layersViewParent, prefWidth, prefHeight));
                layerStage.initModality(Modality.NONE);
                layerStage.setTitle("Entity Layers");
                layerStage.initStyle(StageStyle.UTILITY);
                if(owner != null){
                    layerStage.initOwner(owner);
                }
                //layerStage.setAlwaysOnTop(true);
                layerStage.show();
                layerStage.setOnCloseRequest(event -> {
                    layerStage.hide();
                });
            }else{
                layerStage.show();
            }

        });
    }

}
