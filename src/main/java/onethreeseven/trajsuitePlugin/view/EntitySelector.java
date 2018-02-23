package onethreeseven.trajsuitePlugin.view;

import javafx.scene.control.ChoiceBox;
import onethreeseven.trajsuitePlugin.model.EntitySupplier;
import java.util.Collection;
import java.util.ServiceLoader;

/**
 * A widget populated with relevant entities the program has of a certain type.
 * @author Luke Bermingham
 */
public class EntitySelector<T> extends ChoiceBox<T> {

    private final Class<T> entityType;

    public EntitySelector(Class<T> entityType) {
        this.entityType = entityType;

        boolean addedSomeEntities = false;

        //ask service loaders for entities for this choice box
        ServiceLoader<EntitySupplier> services = ServiceLoader.load(EntitySupplier.class);
        for (EntitySupplier service : services) {
            Collection<T> entities = service.supply(entityType);
            if(!entities.isEmpty()){
                getItems().addAll(entities);
                addedSomeEntities = true;
                getSelectionModel().selectFirst();
            }
        }

        if(!addedSomeEntities){
            this.setDisable(true);
        }


    }
}
