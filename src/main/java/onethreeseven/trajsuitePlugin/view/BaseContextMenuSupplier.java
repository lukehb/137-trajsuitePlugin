package onethreeseven.trajsuitePlugin.view;

import onethreeseven.trajsuitePlugin.model.BaseTrajSuiteProgram;
import onethreeseven.trajsuitePlugin.model.WrappedEntity;
import onethreeseven.trajsuitePlugin.model.WrappedEntityLayer;

/**
 * Useful context menus for all modules
 * @author Luke Bermingham
 */
public class BaseContextMenuSupplier implements EntityContextMenuSupplier {


    @Override
    public void supplyMenuForLayer(ContextMenuPopulator populator, WrappedEntityLayer layer) {

        TrajSuiteMenuItem deleteItem = new TrajSuiteMenuItem("Delete",
                ()-> BaseTrajSuiteProgram.getInstance().getLayers().removeLayer(layer.getLayerName()));

        populator.addMenu(deleteItem);

    }

    @Override
    public void supplyMenuForEntity(ContextMenuPopulator populator, WrappedEntity entity, String parentLayer) {

        TrajSuiteMenuItem deleteItem = new TrajSuiteMenuItem("Delete",
                ()-> BaseTrajSuiteProgram.getInstance().getLayers().remove(parentLayer, entity.getId()));

        populator.addMenu(deleteItem);

    }
}
