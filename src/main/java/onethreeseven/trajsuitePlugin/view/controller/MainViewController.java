package onethreeseven.trajsuitePlugin.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import onethreeseven.trajsuitePlugin.model.BaseTrajSuiteProgram;
import onethreeseven.trajsuitePlugin.view.AbstractMenuBarPopulator;
import onethreeseven.trajsuitePlugin.view.MenuSupplier;
import onethreeseven.trajsuitePlugin.view.TrajSuiteMenu;
import onethreeseven.trajsuitePlugin.view.TrajSuiteMenuItem;

import java.util.Collection;
import java.util.ServiceLoader;

/**
 * Controller for the main view.
 * @author Luke Bermingham
 */
public class MainViewController {

    protected final BaseTrajSuiteProgram program;
    protected final Stage primaryStage;

    public MainViewController(BaseTrajSuiteProgram program, Stage primaryStage) {
        this.program = program;
        this.primaryStage = primaryStage;
    }

    @FXML
    public BorderPane topLevelPane;

    @FXML
    public MenuBar topMenu;

    @FXML
    protected void initialize(){
        populateMenuBar();
    }

    private void populateMenuBar(){

        MenubarPopulator menuBarPopulator = new MenubarPopulator(topMenu);

        ServiceLoader<MenuSupplier> menuServiceLoader = ServiceLoader.load(MenuSupplier.class);
        for (MenuSupplier aMenuService : menuServiceLoader) {
            aMenuService.supplyMenus(menuBarPopulator, program, primaryStage);
        }
    }


    private class MenubarPopulator extends AbstractMenuBarPopulator {

        final MenuBar menubar;

        MenubarPopulator(MenuBar menubar) {
            this.menubar = menubar;
        }

        @Override
        public void addMenu(TrajSuiteMenu menu) {
            if (menubar != null) {

                //check for name clash with existing menus
                String toLookFor = menu.getName();
                Menu topMenu = null;

                for (Menu someExistingMenu : menubar.getMenus()) {
                    if (someExistingMenu.getText().equals(toLookFor)) {
                        topMenu = someExistingMenu;
                        break;
                    }
                }

                if (topMenu == null) {
                    topMenu = new Menu(toLookFor);
                    menubar.getMenus().add(topMenu);
                }

                //now accumulate children
                for (TrajSuiteMenu trajSuiteMenu : menu.getChildren()) {
                    addSubMenu(trajSuiteMenu, topMenu.getItems());
                }
            }
        }

        private void addSubMenu(TrajSuiteMenu menu, Collection<MenuItem> siblingsMenus) {

            //handle adding menu items
            if (menu instanceof TrajSuiteMenuItem) {
                MenuItem menuItem = new MenuItem(menu.getName());
                menuItem.setOnAction(event -> ((TrajSuiteMenuItem) menu).fireOnClick());
                siblingsMenus.add(menuItem);
                return;
            }

            //look in siblings for menu with this name
            Menu existingMenu = null;

            for (MenuItem sibling : siblingsMenus) {
                if (sibling instanceof Menu && sibling.getText().equals(menu.getName())) {
                    existingMenu = (Menu) sibling;
                    break;
                }
            }

            //there was no existing menu with that name, so make one
            if (existingMenu == null) {
                existingMenu = new Menu(menu.getName());
                siblingsMenus.add(existingMenu);
            }

            //accumulate the remaining children
            for (TrajSuiteMenu childMenu : menu.getChildren()) {
                addSubMenu(childMenu, existingMenu.getItems());
            }

        }
    }
}
