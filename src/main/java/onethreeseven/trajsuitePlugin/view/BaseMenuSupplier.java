package onethreeseven.trajsuitePlugin.view;

import onethreeseven.trajsuitePlugin.model.BaseTrajSuiteProgram;

/**
 * Some base menus all builds of TrajSuite will have.
 * @author Luke Bermingham
 */
public class BaseMenuSupplier implements MenuSupplier {
    @Override
    public void supplyMenus(AbstractMenuBarPopulator populator, BaseTrajSuiteProgram program) {

        //File menu
        TrajSuiteMenu fileMenu = new TrajSuiteMenu("File");
        //File >> Exit
        fileMenu.addChild(new TrajSuiteMenuItem("Exit",
                ()-> program.getCLI().doCommand(new String[]{"exit"})));

        populator.addMenu(fileMenu);

    }
}
