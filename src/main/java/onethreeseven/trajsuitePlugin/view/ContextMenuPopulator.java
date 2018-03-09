package onethreeseven.trajsuitePlugin.view;


import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import java.util.List;

/**
 * Populates a context menu based on menus it is passed
 * @author Luke Bermingham
 */
public class ContextMenuPopulator {

    private final ContextMenu contextMenu;

    ContextMenuPopulator(ContextMenu contextMenu){
        this.contextMenu = contextMenu;
    }

    public void addMenu(TrajSuiteMenu menu){
        if(menu instanceof TrajSuiteMenuItem){
            addMenuItem((TrajSuiteMenuItem) menu, contextMenu.getItems());
        }else{
            addMenu(menu, contextMenu.getItems());
        }
    }

    protected void addMenu(TrajSuiteMenu trajsuiteMenu, List<MenuItem> parentMenu){
        Menu menu = new Menu(trajsuiteMenu.name);
        for (TrajSuiteMenu child : trajsuiteMenu.children) {
            if(child.getClass().equals(TrajSuiteMenu.class)){
                addMenu(child, menu.getItems());
            }
            else if(child.getClass().equals(TrajSuiteMenuItem.class)){
                addMenuItem((TrajSuiteMenuItem) child, menu.getItems());
            }
        }
        parentMenu.add(menu);
    }

    protected void addMenuItem(TrajSuiteMenuItem menuItem, List<MenuItem> parentMenu){
        MenuItem item = new MenuItem(menuItem.name);
        item.setOnAction(event -> menuItem.onClick.run());
        parentMenu.add(item);
    }

}
