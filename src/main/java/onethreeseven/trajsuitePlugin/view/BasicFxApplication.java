package onethreeseven.trajsuitePlugin.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import onethreeseven.trajsuitePlugin.model.BaseTrajSuiteProgram;
import onethreeseven.trajsuitePlugin.view.controller.MainViewController;
import java.io.IOException;
import java.net.URL;

/**
 * A simple java fx application that loads the MainView.fxml
 * Useful for testing plugins by making your own instance of this class.
 * @author Luke Bermingham
 */
public abstract class BasicFxApplication extends Application {

    @Override
    public void start(Stage stage) {

        try{
            BaseTrajSuiteProgram program = preStart(stage);

            Parent mainView = loadMainView(program, stage);

            //pass view to stage
            stage.setTitle(getTitle());
            stage.setScene(new Scene(mainView, getStartWidth(), getStartHeight()));
            stage.show();

            //start layers in its own stage here
            ViewUtil.showEntityLayersWindow(program.getLayers(), stage);

        }catch (Exception e){
            System.err.println("Error in pre-start of application");
            e.printStackTrace();
        }

        afterStart(stage);

    }

    protected Parent loadMainView(BaseTrajSuiteProgram program, Stage primaryStage) throws IOException {
        //call fxml
        URL fxmlView = null;
        try {
            fxmlView = BasicFxApplication.class
                    .getResource("/onethreeseven/trajsuitePlugin/view/MainView.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(fxmlView == null){
            throw new IllegalStateException("Could not get the MainView.fxml resource, for some reason it was null.");
        }

        FXMLLoader loader = new FXMLLoader(fxmlView);

        //init view controller with data it needs
        Object controller = initViewController(program, primaryStage);

        //setup of wwd view ready
        loader.setController(controller);

        return loader.load();
    }

    protected void afterStart(Stage stage){};

    protected Object initViewController(BaseTrajSuiteProgram program, Stage primaryStage){
        return new MainViewController(program, primaryStage);
    }

    protected abstract BaseTrajSuiteProgram preStart(Stage stage);

    public abstract String getTitle();

    public abstract int getStartWidth();

    public abstract int getStartHeight();

}
