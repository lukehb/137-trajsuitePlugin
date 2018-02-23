package onethreeseven.trajsuitePlugin.model;

import onethreeseven.jclimod.CLIProgram;


/**
 * Base class that represent some of the functionality plugin implementors
 * may have to interact with to get their plugin into the program.
 * @author Luke Bermingham
 */
public class BaseTrajSuiteProgram {

    protected final Layers layers;
    protected final CLIProgram program;

    public BaseTrajSuiteProgram(){
        this.layers = makeLayers();
        this.program = makeCLI();

    }

    protected CLIProgram makeCLI(){
        return new CLIProgram(new Object[]{layers});
    }

    protected Layers makeLayers(){
        return new Layers();
    }

    public Layers getLayers() {
        return layers;
    }

    public CLIProgram getCLI() {
        return program;
    }
}
