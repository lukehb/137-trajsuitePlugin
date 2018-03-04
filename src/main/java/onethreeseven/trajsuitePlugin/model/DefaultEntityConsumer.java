package onethreeseven.trajsuitePlugin.model;

import java.util.ServiceLoader;

/**
 * Consume entities and adds them to the layers.
 * However if any other entity consumers exist this
 * one does not come into effect and instead does no consumption.
 * @author Luke Bermingham
 */
public class DefaultEntityConsumer implements EntityConsumer {

    @Override
    public void consume(String layername, String id, Object toConsume) {

        //check for other services
        ServiceLoader<EntityConsumer> serviceLoader = ServiceLoader.load(EntityConsumer.class);
        //if there is another entity that is not this one no need for this one
        for (EntityConsumer entityConsumer : serviceLoader) {
            if(!(entityConsumer instanceof DefaultEntityConsumer)){
                return;
            }
        }
        //made it here, means this is the only consumer
        BaseTrajSuiteProgram.getInstance().getLayers().add(layername, id, toConsume);

    }

}
