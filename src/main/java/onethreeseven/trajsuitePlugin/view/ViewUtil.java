package onethreeseven.trajsuitePlugin.view;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import onethreeseven.trajsuitePlugin.model.Layers;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Some utils for dealing with views
 * @author Luke Bermingham
 */
public final class ViewUtil {

    private static Stage layerStage = null;

    public static void loadUtilityView(Stage primaryStage, String title, Parent viewParent){
        Stage stage = new Stage();
        stage.initModality(Modality.NONE);
        //stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UTILITY);
        stage.setTitle(title);
        stage.setScene(new Scene(viewParent));
        stage.initOwner(primaryStage);
        stage.show();
    }

    public static void loadUtilityView(Class callerViewClass, Stage primaryStage, String title, String fxmlFile){
        URL res = callerViewClass.getResource(fxmlFile);

        if(res == null){
            throw new IllegalArgumentException(fxmlFile + " view fxml resource could not be found.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(res);
            Parent view = loader.load();
            loadUtilityView(primaryStage, title, view);
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
                double halfScreen = Screen.getPrimary().getVisualBounds().getHeight() * 0.5;

                layerStage.setY(halfScreen - (prefHeight * 0.5));



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

    public static void showInformationWindow(String title, Map<String, String> information){

        Stage infoStage = new Stage();
        infoStage.initModality(Modality.NONE);
        infoStage.setTitle(title);
        infoStage.initStyle(StageStyle.UTILITY);

        GridPane pane = new GridPane();
        int row = 0;
        for (Map.Entry<String, String> entry : information.entrySet()) {

            Label infoLabel = new Label(entry.getKey());
            GridPane.setHalignment(infoLabel, HPos.RIGHT);

            Label valueLabel = new Label(entry.getValue());
            GridPane.setHalignment(valueLabel, HPos.LEFT);

            pane.add(infoLabel, 0, row);
            pane.add(valueLabel, 1, row);

            row++;
        }

        pane.setHgap(10);
        for (ColumnConstraints columnConstraints : pane.getColumnConstraints()) {
            columnConstraints.setPrefWidth(100);
        }
        pane.setPadding(new Insets(10));

        Scene scene = new Scene(pane);
        infoStage.setScene(scene);

        infoStage.show();
    }


}
