package onethreeseven.trajsuitePlugin.model;

import onethreeseven.trajsuitePlugin.transaction.AddEntitiesTransaction;
import onethreeseven.trajsuitePlugin.transaction.RemoveEntitiesTransaction;

/**
 * Consume entities and adds them to the layers.
 * However if any other entity consumers exist this
 * one does not come into effect and instead does no consumption.
 * @author Luke Bermingham
 */
public class DefaultTransactionProcessor implements TransactionProcessor {


    @Override
    public void process(AddEntitiesTransaction transaction) {
        BaseTrajSuiteProgram.getInstance().getLayers().process(transaction);
    }

    @Override
    public void process(RemoveEntitiesTransaction transaction) {
        BaseTrajSuiteProgram.getInstance().getLayers().process(transaction);
    }

}
