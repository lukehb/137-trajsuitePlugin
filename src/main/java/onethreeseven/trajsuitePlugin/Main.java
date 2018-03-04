package onethreeseven.trajsuitePlugin;


import javafx.stage.Stage;
import onethreeseven.trajsuitePlugin.model.BaseTrajSuiteProgram;
import onethreeseven.trajsuitePlugin.view.BasicFxApplication;


/**
 * Entry point for running this module.
 * @author Luke Bermingham
 */
public class Main extends BasicFxApplication {

    @Override
    protected BaseTrajSuiteProgram preStart(Stage stage) {

        BaseTrajSuiteProgram program = BaseTrajSuiteProgram.getInstance();

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


    }
}
