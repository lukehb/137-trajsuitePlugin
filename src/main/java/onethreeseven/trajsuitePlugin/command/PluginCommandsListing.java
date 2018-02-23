package onethreeseven.trajsuitePlugin.command;

import com.beust.jcommander.JCommander;
import onethreeseven.jclimod.AbstractCommandsListing;
import onethreeseven.jclimod.CLICommand;
import onethreeseven.trajsuitePlugin.model.Layers;

import java.util.ArrayList;

/**
 * Commands that all plugins may find useful.
 * @author Luke Bermingham
 */
public class PluginCommandsListing extends AbstractCommandsListing {
    @Override
    protected CLICommand[] createCommands(JCommander jc, Object... args) {

        ArrayList<CLICommand> commands = new ArrayList<>();

        for (Object arg : args) {

            if(arg instanceof Layers){
                commands.add(new ListEntityHierarchyCommand((Layers) arg));
            }
        }

        CLICommand[] commandsArr = new CLICommand[commands.size()];
        commands.toArray(commandsArr);
        return commandsArr;
    }
}
