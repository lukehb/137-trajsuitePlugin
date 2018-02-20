package onethreeseven.trajsuitePlugin.view;


import onethreeseven.trajsuitePlugin.model.AbstractTrajSuiteProgram;

/**
 * Supplies menus, which TrajSuite access through a service loader
 * and loads into its view. So, if you want to add menus to TrajSuite for
 * your plugin make sure you implement this interface and provide it to
 * this package in your module-info.java
 * @author Luke Bermingham
 */
public interface MenuSupplier {

    /**
     * Implementors should make their relevant {@link TrajSuiteMenu}
     * and then add it to the program using {@link AbstractMenuBarPopulator#addMenu(TrajSuiteMenu)}.
     * @param populator An instance of class that allows adding items to the menu bar.
     * @param program An instance of the TrajSuite program that implementor can expect to be passed.
     */
    void supplyMenus(AbstractMenuBarPopulator populator, AbstractTrajSuiteProgram program);

}
