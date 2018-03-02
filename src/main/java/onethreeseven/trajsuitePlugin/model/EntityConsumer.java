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
 * Once doing so, the developer can iterate the loaded consumers and they can pass the entity they wish to be loaded into Trajsuite using the {@link EntityConsumer#consume(String, String, Object)} method.
 *
 * @author Luke Bermingham
 */
public interface EntityConsumer {


    /**
     * Consumes some object and adds it to a layer with this name.
     * @param layername The name of the layer.
     * @param id The id to assign the consumed object.
     * @param toConsume The object to consume.
     */
    void consume(String layername, String id, Object toConsume);

}
