package onethreeseven.trajsuitePlugin.view;

import onethreeseven.trajsuitePlugin.model.AbstractTrajSuiteProgram;

/**
 * Some base menus all builds of TrajSuite will have.
 * @author Luke Bermingham
 */
public class BaseMenuSupplier implements MenuSupplier {
    @Override
    public void supplyMenus(AbstractMenuBarPopulator populator, AbstractTrajSuiteProgram program) {

        //File menu
        TrajSuiteMenu fileMenu = new TrajSuiteMenu("File");
        //File >> Exit
        fileMenu.addChild(new TrajSuiteMenuItem("Exit",
                ()-> program.getCLI().doCommand(new String[]{"exit"})));

        populator.addMenu(fileMenu);

    }
}
