package onethreeseven.trajsuitePlugin.model;

import onethreeseven.jclimod.CLIProgram;

import java.util.ServiceLoader;


/**
 * Base class that represent some of the functionality plugin implementors
 * may have to interact with to get their plugin into the program.
 * @author Luke Bermingham
 */
public class BaseTrajSuiteProgram {

    protected final Layers layers;
    protected final CLIProgram program;

    protected BaseTrajSuiteProgram(){
        this.layers = makeLayers();
        this.program = makeCLI();
    }

    /////////////////////
    //static accessor
    /////////////////////

    //singleton
    private static BaseTrajSuiteProgram inst;
    public static BaseTrajSuiteProgram getInstance(){
        if(inst == null){
            //user service loader to initalise a program
            ServiceLoader<ProgramSupplier> serviceLoader = ServiceLoader.load(ProgramSupplier.class);
            for (ProgramSupplier programSupplier : serviceLoader) {
                BaseTrajSuiteProgram prog = programSupplier.supply();
                if(prog != null){
                    inst = prog;
                    return inst;
                }
            }
        }
        return inst;
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
