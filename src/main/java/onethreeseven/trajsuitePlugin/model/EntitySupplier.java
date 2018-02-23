package onethreeseven.trajsuitePlugin.model;

import java.util.Collection;

/**
 * Interface for Services to supply entities.
 * @author Luke Bermingham
 */
public interface EntitySupplier {

    /**
     * Supply all entities of a certain type.
     * @param classType The type of entity we want.
     * @param <T> The type of entity we want.
     * @return An entity of the specified type, if available, or null.
     */
    public <T> Collection<T> supply(Class<T> classType);

}
