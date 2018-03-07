package onethreeseven.trajsuitePlugin.view;

/**
 * TrajSuite extends this class so its menu bar can be populated.
 * @author Luke Bermingham
 */
public abstract class AbstractMenuBarPopulator {


    /**
     * Add a menu to the program's view.
     * @param menu The menus to accumulate.
     */
    public abstract void addMenu(TrajSuiteMenu menu);


}
