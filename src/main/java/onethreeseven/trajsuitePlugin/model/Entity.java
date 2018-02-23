package onethreeseven.trajsuitePlugin.model;

/**
 * Simple interface for entities, they have ids
 * @author Luke Bermingham
 */
public interface Entity extends Selectable {

    /**
     * The id of the entity, which is unique per entity type.
     * But two different types of entities may have the same id.
     * For example two dogs will never have the same id.
     * But a dog and a cat could end up with the same id.
     * @return The id.
     */
    String getId();

}
