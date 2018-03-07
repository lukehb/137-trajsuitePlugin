package onethreeseven.trajsuitePlugin.model;

import onethreeseven.trajsuitePlugin.transaction.AddEntitiesTransaction;
import onethreeseven.trajsuitePlugin.transaction.RemoveEntitiesTransaction;

/**
 * Inherit this interface to handle transactions in TrajSuite (accumulate, removing, editing of entities).
 *
 * <br>
 *
 * For use of this a service, a plugin developer will want to call {@link java.util.ServiceLoader#load(Class)}, like so:
 *
 * <p>{@code ServiceLoader<EntityConsumer>.Load(TransactionProcessor.class)}</p>
 *
 * @author Luke Bermingham
 */
public interface TransactionProcessor {


    void process(AddEntitiesTransaction transaction);
    void process(RemoveEntitiesTransaction transaction);

}
