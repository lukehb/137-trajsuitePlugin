package onethreeseven.trajsuitePlugin.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link Layers}
 * @author Luke Bermingham
 */
public class LayersTest {

    private static final Object[] models = new Object[]{
            "Yo", "How's", "It?", 137
    };

    private static final String[] entityIds = new String[]{
            "1", "2", "3", "4"
    };

    private static final String layername = "testLayer";

    public Layers initLayers(){
        final Layers layers = new Layers();
        for (int i = 0; i < models.length; i++) {
            layers.add(layername, entityIds[i], models[i]);
        }
        return layers;
    }

    @Test
    public void testAddEntities() {

        Layers layers = initLayers();

        for (int i = 0; i < models.length; i++) {
            WrappedEntity entity = layers.getEntity(layername, entityIds[i]);
            Object retrievedModel = entity.model;
            Assert.assertTrue(retrievedModel.equals(models[i]));
        }

    }

    @Test
    public void testGetLayersByType() {

        Layers layers = initLayers();

        WrappedEntityLayer strLayer = layers.getLayer(layername);

        for (int i = 0; i < entityIds.length; i++) {

            String id = entityIds[i];
            WrappedEntity entity = strLayer.get(id);
            Object retrievedModel = entity.model;
            Assert.assertTrue(retrievedModel.equals(models[i]));

        }

    }
}