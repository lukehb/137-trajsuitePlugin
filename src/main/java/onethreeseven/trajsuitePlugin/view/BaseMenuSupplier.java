package onethreeseven.trajsuitePlugin.view;

import javafx.stage.Stage;
import onethreeseven.trajsuitePlugin.model.BaseTrajSuiteProgram;

/**
 * Some base menus all builds of TrajSuite will have.
 * @author Luke Bermingham
 */
public class BaseMenuSupplier implements MenuSupplier {
    @Override
    public void supplyMenus(AbstractMenuBarPopulator populator, BaseTrajSuiteProgram program, Stage stage) {

        //File menu
        TrajSuiteMenu fileMenu = new TrajSuiteMenu("File", -99);
        //File >> Exit
        fileMenu.addChild(new TrajSuiteMenuItem("Exit",
                ()-> program.getCLI().doCommand(new String[]{"exit"})));

        //View menu
        TrajSuiteMenu viewMenu = new TrajSuiteMenu("View", 10);
        //View >> Entity Layers
        viewMenu.addChild(new TrajSuiteMenuItem("Entity Layers",
                ()-> ViewUtil.showEntityLayersWindow(stage)));

        populator.addMenu(fileMenu);
        populator.addMenu(viewMenu);

    }
}
