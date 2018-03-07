package onethreeseven.trajsuitePlugin.model;

import onethreeseven.trajsuitePlugin.transaction.AddEntitiesTransaction;
import onethreeseven.trajsuitePlugin.transaction.RemoveEntitiesTransaction;
import java.util.ServiceLoader;

/**
 * Consume entities and adds them to the layers.
 * However if any other entity consumers exist this
 * one does not come into effect and instead does no consumption.
 * @author Luke Bermingham
 */
public class DefaultTransactionProcessor implements TransactionProcessor {

    protected boolean isOnlyProcessor(){
        //check for other services
        ServiceLoader<TransactionProcessor> serviceLoader = ServiceLoader.load(TransactionProcessor.class);
        //if there is another entity that is not this one no need for this one
        for (TransactionProcessor entityConsumer : serviceLoader) {
            if(!(entityConsumer instanceof DefaultTransactionProcessor)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void process(AddEntitiesTransaction transaction) {
        if(isOnlyProcessor()){
            BaseTrajSuiteProgram.getInstance().getLayers().process(transaction);
        }
    }

    @Override
    public void process(RemoveEntitiesTransaction transaction) {
        if(isOnlyProcessor()){
            BaseTrajSuiteProgram.getInstance().getLayers().process(transaction);
        }
    }

}
