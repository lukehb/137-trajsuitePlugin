package onethreeseven.trajsuitePlugin.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Represent a menu which can contain either more menus or clickable {@link TrajSuiteMenuItem}.
 * @author Luke Bermingham
 */
public class TrajSuiteMenu {

    protected final String name;
    protected final PriorityQueue<TrajSuiteMenu> children;
    protected final int order;

    public TrajSuiteMenu(String name, Collection<TrajSuiteMenu> children, int order){
        this.name = name;
        this.children = new PriorityQueue<>(new Comparator<TrajSuiteMenu>() {
            @Override
            public int compare(TrajSuiteMenu o1, TrajSuiteMenu o2) {
                return Integer.compare(o1.order, o2.order);
            }
        });
        if(children != null){
            this.children.addAll(children);
        }
        this.order = order;
    }

    public TrajSuiteMenu(String name, int order) {
        this(name, new ArrayList<>(), order);
    }

    public void addChild(TrajSuiteMenu child){
        if(this.children == null){
            throw new IllegalStateException("Cannot accumulate a menu item when this menu was initialised with null children.");
        }
        this.children.add(child);
    }

    public int getOrder() {
        return order;
    }

    public PriorityQueue<TrajSuiteMenu> getChildren() {
        return children;
    }

    public String getName() {
        return name;
    }
}
