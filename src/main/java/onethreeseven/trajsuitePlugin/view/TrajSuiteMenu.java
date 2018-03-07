package onethreeseven.trajsuitePlugin.view;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represent a menu which can contain either more menus or clickable {@link TrajSuiteMenuItem}.
 * @author Luke Bermingham
 */
public class TrajSuiteMenu {

    protected final String name;
    protected final Collection<TrajSuiteMenu> children;

    public TrajSuiteMenu(String name, Collection<TrajSuiteMenu> children){
        this.name = name;
        this.children = children;
    }

    public TrajSuiteMenu(String name) {
        this(name, new ArrayList<>());
    }

    public void addChild(TrajSuiteMenu child){
        if(this.children == null){
            throw new IllegalStateException("Cannot accumulate a menu item when this menu was initialised with null children.");
        }
        this.children.add(child);
    }

    public Collection<TrajSuiteMenu> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }
}
