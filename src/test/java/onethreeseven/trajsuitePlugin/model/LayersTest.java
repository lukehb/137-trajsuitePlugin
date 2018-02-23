package onethreeseven.trajsuitePlugin.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Tests for {@link Layers}
 * @author Luke Bermingham
 */
public class LayersTest {


    @Test
    public void testAddEntities() {

        final Layers layers = new Layers();

        WrappedEntity<String> entityStr1 = layers.add("1", "Yo");
        WrappedEntity<String> entityStr2 = layers.add("2", "How's");
        WrappedEntity<String> entityStr3 = layers.add("3", "It?");
        WrappedEntity<Integer> entityInt1 = layers.add("4", 137);

        WrappedEntity<String> entityStr1Retrieved = layers.getEntity("1", String.class);
        WrappedEntity<String> entityStr2Retrieved = layers.getEntity("2", String.class);
        WrappedEntity<String> entityStr3Retrieved = layers.getEntity("3", String.class);
        WrappedEntity<Integer> entityInt1Retrieved = layers.getEntity("4", Integer.class);

        Assert.assertTrue(entityStr1.model.equals(entityStr1Retrieved.model));
        Assert.assertTrue(entityStr2.model.equals(entityStr2Retrieved.model));
        Assert.assertTrue(entityStr3.model.equals(entityStr3Retrieved.model));
        Assert.assertTrue(entityInt1.model.equals(entityInt1Retrieved.model));

    }

    @Test
    public void testGetLayersByType() {

        final Layers layers = new Layers();

        WrappedEntity<String> entityStr1 = layers.add("1", "Yo");
        WrappedEntity<String> entityStr2 = layers.add("2", "How's");
        WrappedEntity<String> entityStr3 = layers.add("3", "It?");

        WrappedEntityLayer<String> strLayer = layers.getLayerByType(String.class);

        Assert.assertTrue(strLayer.get("1").model.equals(entityStr1.model));
        Assert.assertTrue(strLayer.get("2").model.equals(entityStr2.model));
        Assert.assertTrue(strLayer.get("3").model.equals(entityStr3.model));

    }

    @Test
    public void testIter() {

        final Layers layers = new Layers();

        WrappedEntity<String> entityStr1 = layers.add("1", "Yo");
        WrappedEntity<String> entityStr2 = layers.add("2", "How's");
        WrappedEntity<String> entityStr3 = layers.add("3", "It?");

        Iterator<WrappedEntityLayer> iter = layers.iterator();

        Assert.assertTrue(iter.hasNext());

        WrappedEntityLayer layer = iter.next();

        Assert.assertTrue(layer.get("1").model.equals(entityStr1.model));
        Assert.assertTrue(layer.get("2").model.equals(entityStr2.model));
        Assert.assertTrue(layer.get("3").model.equals(entityStr3.model));


    }
}