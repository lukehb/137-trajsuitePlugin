package onethreeseven.trajsuitePlugin.command;

import onethreeseven.jclimod.CLICommand;
import onethreeseven.trajsuitePlugin.model.Entity;
import onethreeseven.trajsuitePlugin.model.EntityLayer;
import onethreeseven.trajsuitePlugin.model.Layers;

/**
 * Command to list entities.
 * @author Luke Bermingham
 */
public class ListEntityHierarchyCommand extends CLICommand {

    private final Layers layers;

    ListEntityHierarchyCommand(Layers layers){
        this.layers = layers;
    }

    @Override
    public String getCommandName() {
        return "listEntities";
    }

    @Override
    public String[] getOtherCommandNames() {
        return new String[]{"le"};
    }

    @Override
    public String getDescription() {
        return "Lists all entities loaded into the program.";
    }

    @Override
    protected String getUsage() {
        return "le";
    }

    @Override
    protected boolean parametersValid() {
        return true;
    }

    @Override
    protected boolean runImpl() {
        boolean listedSomething = false;
        for (EntityLayer layer : layers) {
            System.out.println(format(layer));
            for (Object obj : layer) {
                if(obj instanceof Entity){
                    listedSomething = true;
                    System.out.println(format((Entity) obj));
                }
            }
        }
        if(!listedSomething){
            System.out.println("No entities loaded yet.");
        }

        return true;
    }

    @Override
    public boolean shouldStoreRerunAlias() {
        return false;
    }

    @Override
    public String generateRerunAliasBasedOnParams() {
        return null;
    }

    @Override
    public String getCategory() {
        return "General";
    }

    private static final String layerFmt = "[%s]%s";
    private static final String entityFmt = "    %s%s";

    private String format(Entity entity){
        String selectedSymbol = entity.isSelected() ? "*" : "";
        return String.format(entityFmt, entity.toString(), selectedSymbol);
    }

    private String format(EntityLayer layer){
        String selectedSymbol = layer.isSelected() ? "*" : "";
        return String.format(layerFmt, layer.getLayerName(), selectedSymbol);
    }

}
