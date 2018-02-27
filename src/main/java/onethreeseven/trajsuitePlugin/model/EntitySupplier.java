package onethreeseven.trajsuitePlugin.model;

import java.util.Collection;
import java.util.Map;

/**
 * Interface for Services to supply entities.
 * @author Luke Bermingham
 */
public interface EntitySupplier {

    public <T> T supply(String id, String layername, Class<T> modelType);

    public <T> Collection<T> supply(String layername, Class<T> modelType);

    /**
     * Supply all entities of a certain type, regardless of selection.
     * @param modelType The type of model we want.
     * @param <T> The type of model we want.
     * @return An entity of the specified type, if available, or null.
     */
    public <T> Collection<T> supply(Class<T> modelType);

    /**
     * @return Returns a map of all selected entities in a map where the key is
     * the models type and the value is collection of all models whose entities were selected.
     */
    public Map<Class, Collection<Object>> supplyAllSelected();

    /**
     * Gets all selected entities whose class types exist in the passed in map.
     * Use this function to filter certain types of entities.
     * @param toPopulate A map of model types to populate with selected entity's models.
     */
    public void supplyAllSelectedByTypes(Map<Class, Collection<Object>> toPopulate);

}
