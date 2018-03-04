import onethreeseven.trajsuitePlugin.command.PluginCommandsListing;
import onethreeseven.trajsuitePlugin.model.*;

open module onethreeseven.trajsuitePlugin {

    requires transitive onethreeseven.jclimod;
    requires transitive onethreeseven.geo;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires java.prefs;
    requires java.desktop;
    requires javafx.swing;
    requires jcommander;

    //for menu items
    uses onethreeseven.trajsuitePlugin.view.MenuSupplier;
    provides onethreeseven.trajsuitePlugin.view.MenuSupplier with onethreeseven.trajsuitePlugin.view.BaseMenuSupplier;

    //for cli commands all plugins need
    provides onethreeseven.jclimod.AbstractCommandsListing with PluginCommandsListing;

    //for entity selection from drop down based on layers
    uses onethreeseven.trajsuitePlugin.model.EntitySupplier;
    provides EntitySupplier with LayersBasedEntitySupplier;

    //for entity consumption
    uses onethreeseven.trajsuitePlugin.model.EntityConsumer;
    provides EntityConsumer with DefaultEntityConsumer;

    uses onethreeseven.trajsuitePlugin.model.ProgramSupplier;
    provides onethreeseven.trajsuitePlugin.model.ProgramSupplier with DefaultProgramSupplier;

    //general exports
    exports onethreeseven.trajsuitePlugin.view;
    exports onethreeseven.trajsuitePlugin.model;
    exports onethreeseven.trajsuitePlugin.util;
    exports onethreeseven.trajsuitePlugin.graphics;
    exports onethreeseven.trajsuitePlugin.settings;
    exports onethreeseven.trajsuitePlugin.view.controller;

    //for javafx to work
    exports onethreeseven.trajsuitePlugin;

}