package onethreeseven.trajsuitePlugin.model;

import java.util.ServiceLoader;

/**
 * Supplies a program, if there are no other program suppliers.
 * @author Luke Bermingham
 */
public class DefaultProgramSupplier implements ProgramSupplier {
    @Override
    public BaseTrajSuiteProgram supply() {

        ServiceLoader<ProgramSupplier> serviceLoader = ServiceLoader.load(ProgramSupplier.class);

        //if there is any other program supplier let that take precedence over this one
        for (ProgramSupplier programSupplier : serviceLoader) {
            if(!(programSupplier instanceof DefaultProgramSupplier)){
                return null;
            }
        }

        //otherwise, made it here, this is the only supplier, so, supply

        return new BaseTrajSuiteProgram();
    }
}
