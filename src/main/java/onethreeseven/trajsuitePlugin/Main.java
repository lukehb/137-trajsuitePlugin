package onethreeseven.trajsuitePlugin;


import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import onethreeseven.trajsuitePlugin.model.BaseTrajSuiteProgram;
import onethreeseven.trajsuitePlugin.view.BasicFxApplication;
import onethreeseven.trajsuitePlugin.view.VisibilityWidget;


/**
 * Entry point for running this module.
 * @author Luke Bermingham
 */
public class Main extends BasicFxApplication {

    private final BaseTrajSuiteProgram program = new BaseTrajSuiteProgram();

    @Override
    protected BaseTrajSuiteProgram preStart(Stage stage) {



        program.getLayers().add("Test");
        program.getLayers().add(137);
        program.getLayers().add(14);
        program.getLayers().add(42);



        return program;
    }

    @Override
    public String getTitle() {
        return "Plugin Stub";
    }

    @Override
    public int getStartWidth() {
        return 640;
    }

    @Override
    public int getStartHeight() {
        return 480;
    }

    @Override
    protected void afterStart(Stage stage) {

        Parent parentNode = stage.getScene().getRoot();
        if(parentNode instanceof BorderPane){
            ((BorderPane) parentNode).setBottom(new VisibilityWidget(true));
        }

    }
}
