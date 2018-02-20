package onethreeseven.trajsuitePlugin.model;

/**
 * Inherit this interface to make a sink for entities that should be loaded into TrajSuite.
 *
 * <br>
 *
 * Typically though, a plugin developer will want to call {@link java.util.ServiceLoader#load(Class)}, like so:
 *
 * <p>{@code ServiceLoader<EntityConsumer>.Load(EntityConsumer.class)}</p>
 *
 * Once doing so, the developer can iterate the loaded consumers and they can pass the entity they wish to be loaded into Trajsuite using the {@link EntityConsumer#consume(Object)} method.
 *
 * @author Luke Bermingham
 */
public interface EntityConsumer {

    /**
     * Consumes any object.
     * What happens to object is implementation dependent, but the main entity consumer
     * in TrajSuite just loads this object into the layer stack so that is can be used as
     * input for something else or visualised.
     * @param toConsume The object to consume.
     */
    void consume(Object toConsume);

    /**
     * Consumes and object and assigns it the passed id.
     * @param id The id to assign the consumed object.
     * @param toConsume The object to consume.
     */
    void consume(String id, Object toConsume);

}
