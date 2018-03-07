package onethreeseven.trajsuitePlugin.transaction;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
public class RemoveEntitiesTransaction extends EntityTransaction<RemoveEntityUnit> {

    public RemoveEntitiesTransaction(){
        super();
    }

    public RemoveEntitiesTransaction add(String layername, String id){
        this.data.add(new RemoveEntityUnit(layername, id));
        return this;
    }

}
