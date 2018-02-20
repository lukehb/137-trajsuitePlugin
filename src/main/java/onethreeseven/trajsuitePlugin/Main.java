package onethreeseven.trajsuitePlugin;

import javafx.stage.Stage;
import onethreeseven.jclimod.CLIProgram;
import onethreeseven.trajsuitePlugin.model.AbstractTrajSuiteProgram;
import onethreeseven.trajsuitePlugin.view.BasicFxApplication;

/**
 * Entry point for running this module.
 * @author Luke Bermingham
 */
public class Main extends BasicFxApplication {


    @Override
    protected AbstractTrajSuiteProgram preStart(Stage stage) {
        return new AbstractTrajSuiteProgram() {
            final CLIProgram program = new CLIProgram();
            @Override
            public CLIProgram getCLI() {
                return program;
            }
        };
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
