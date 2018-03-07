package onethreeseven.trajsuitePlugin.view;


import javafx.stage.Stage;
import onethreeseven.trajsuitePlugin.model.BaseTrajSuiteProgram;

/**
 * Supplies menus, which TrajSuite access through a service loader
 * and loads into its view. So, if you want to accumulate menus to TrajSuite for
 * your plugin make sure you implement this interface and provide it to
 * this package in your module-info.java
 * @author Luke Bermingham
 */
public interface MenuSupplier {

    /**
     * Implementors should make their relevant {@link TrajSuiteMenu}
     * and then accumulate it to the program using {@link AbstractMenuBarPopulator#addMenu(TrajSuiteMenu)}.
     * @param populator An instance of class that allows adding items to the menu bar.
     * @param program An instance of the TrajSuite program that implementor can expect to be passed.
     * @param primaryStage The primary stage of the application.
     */
    void supplyMenus(AbstractMenuBarPopulator populator, BaseTrajSuiteProgram program, Stage primaryStage);

}
