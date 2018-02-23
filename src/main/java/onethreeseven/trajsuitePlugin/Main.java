package onethreeseven.trajsuitePlugin;


import javafx.stage.Stage;
import onethreeseven.trajsuitePlugin.model.BaseTrajSuiteProgram;
import onethreeseven.trajsuitePlugin.view.BasicFxApplication;


/**
 * Entry point for running this module.
 * @author Luke Bermingham
 */
public class Main extends BasicFxApplication {

    private final BaseTrajSuiteProgram program = new BaseTrajSuiteProgram();

    @Override
    protected BaseTrajSuiteProgram preStart(Stage stage) {
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
}
