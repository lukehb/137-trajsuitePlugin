package onethreeseven.trajsuitePlugin.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import onethreeseven.trajsuitePlugin.model.Layers;

import java.io.IOException;
import java.net.URL;

/**
 * Some utils for dealing with views
 * @author Luke Bermingham
 */
public final class ViewUtil {

    private static Stage layerStage = null;

    public static void loadUtilityView(Class callerViewClass, Stage primaryStage, String title, String fxmlFile){
        URL res = callerViewClass.getResource(fxmlFile);

        if(res == null){
            throw new IllegalArgumentException(fxmlFile + " view fxml resource could not be found.");
        }

        try {

            FXMLLoader loader = new FXMLLoader(res);

            Parent view = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.NONE);
            //stage.setAlwaysOnTop(true);
            stage.initStyle(StageStyle.UTILITY);
            stage.setTitle(title);
            stage.setScene(new Scene(view));
            stage.initOwner(primaryStage);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showEntityLayersWindow(Stage owner){
        Platform.runLater(()->{
            if(layerStage == null){
                layerStage = new Stage();
                layerStage.setX(0);

                final int prefWidth = 300;
                final int prefHeight = 600;

                //put layers in scroll pane
                //ScrollPane layersViewParent = new ScrollPane();
                BorderPane contentParent = new BorderPane();
                contentParent.setPrefHeight(prefHeight);
                contentParent.setPrefWidth(prefWidth);
                contentParent.setCenter(new EntityTreeView());
                //layersViewParent.setContent(contentParent);

                layerStage.setScene(new Scene(contentParent, prefWidth, prefHeight));
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
