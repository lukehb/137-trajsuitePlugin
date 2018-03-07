package onethreeseven.trajsuitePlugin.view;

import java.util.Collection;

/**
 * Represents a menu item that can be clicked to perform some task.
 * @author Luke Bermingham
 */
public class TrajSuiteMenuItem extends TrajSuiteMenu {

    protected final Runnable onClick;

    public TrajSuiteMenuItem(String menuItemName, Runnable onClick) {
        super(menuItemName, null);
        this.onClick = onClick;
    }

    public void fireOnClick(){
        this.onClick.run();
    }

    @Override
    public void addChild(TrajSuiteMenu child) {
        throw new UnsupportedOperationException("Cannot accumulate child to a menu item, can only accumulate a child to a menu.");
    }

    @Override
    public Collection<TrajSuiteMenu> getChildren() {
        throw new UnsupportedOperationException("Cannot get child of a menu item, only menus can have children.");
    }
}
