package onethreeseven.trajsuitePlugin;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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


        program.getLayers().add("Test").isSelectedProperty().addListener((observable, oldValue, newValue) -> System.out.println("Test selected: " + newValue));
        program.getLayers().add("Yo").isSelectedProperty().addListener((observable, oldValue, newValue) -> System.out.println("Yo selected: " + newValue));
        program.getLayers().add(137).isSelectedProperty().addListener((observable, oldValue, newValue) -> System.out.println("137 selected: " + newValue));
        program.getLayers().add(14).isSelectedProperty().addListener((observable, oldValue, newValue) -> System.out.println("14 selected: " + newValue));
        program.getLayers().add(42).isSelectedProperty().addListener((observable, oldValue, newValue) -> System.out.println("42 selected: " + newValue));

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
