package onethreeseven.trajsuitePlugin.transaction;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Todo: write documentation
 *
 * @author Luke Bermingham
 */
class EntityTransaction<T extends TransactionUnit> {

    protected final Collection<T> data;

    EntityTransaction(Collection<T> data){
        this.data = data;
    }

    EntityTransaction(){
        this.data = new ArrayList<>();
    }

    public EntityTransaction<T> add(T row){
        this.data.add(row);
        return this;
    }

    public void addAll(EntityTransaction<T> transaction){
        this.data.addAll(transaction.data);
    }

    public Collection<T> getData() {
        return data;
    }
}
