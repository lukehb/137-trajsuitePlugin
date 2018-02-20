package onethreeseven.trajsuitePlugin.model;

import onethreeseven.jclimod.CLIProgram;


/**
 * Abstract base class that represent some of the functionality plugin implementors
 * may have to interact with to get their plugin into the program.
 * @author Luke Bermingham
 */
public abstract class AbstractTrajSuiteProgram {

    /**
     * @return An instance of TrajSuite's CLI.
     */
    public abstract CLIProgram getCLI();

}
