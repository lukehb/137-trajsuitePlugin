open module onethreeseven.trajsuitePlugin {

    requires transitive onethreeseven.jclimod;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    uses onethreeseven.trajsuitePlugin.view.MenuSupplier;
    provides onethreeseven.trajsuitePlugin.view.MenuSupplier with onethreeseven.trajsuitePlugin.view.BaseMenuSupplier;

    exports onethreeseven.trajsuitePlugin.view;
    exports onethreeseven.trajsuitePlugin.model;

    //for javafx to work
    exports onethreeseven.trajsuitePlugin;

}