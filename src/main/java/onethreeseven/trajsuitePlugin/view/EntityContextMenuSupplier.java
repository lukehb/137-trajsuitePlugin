package onethreeseven.trajsuitePlugin.view;

import onethreeseven.trajsuitePlugin.model.WrappedEntity;
import onethreeseven.trajsuitePlugin.model.WrappedEntityLayer;

/**
 * An interface to extend and provide to in your module definition if
 * you want your module to have context menus specific to certain entities.
 * @author Luke Bermingham
 */
public interface EntityContextMenuSupplier {

    void supplyMenuForLayer(ContextMenuPopulator populator, WrappedEntityLayer layer);
    void supplyMenuForEntity(ContextMenuPopulator populator, WrappedEntity entity, String parentLayer);
}
