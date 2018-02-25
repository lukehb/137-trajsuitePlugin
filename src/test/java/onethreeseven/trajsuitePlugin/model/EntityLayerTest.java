package onethreeseven.trajsuitePlugin.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test for {@link EntityLayer}
 * @author Luke Bermingham
 */
public class EntityLayerTest {

    @Test
    public void testAddAndChangeSelection() {

        EntityLayer<WrappedEntity<String>> layer = new EntityLayer<>("Mock", String.class);

        WrappedEntity<String> lerry = new WrappedEntity<>("Larry");
        lerry.isSelectedProperty().set(true);
        WrappedEntity<String> curly = new WrappedEntity<>("Curly");
        curly.isSelectedProperty().set(true);
        WrappedEntity<String> moe = new WrappedEntity<>("Moe");
        moe.isSelectedProperty().set(true);

        layer.add(lerry);
        layer.add(curly);
        layer.add(moe);

        //layer should be selected
        Assert.assertTrue(layer.isSelectedProperty().getValue());


        lerry.isSelectedProperty().set(false);

        //layer should be deselected now that lerry is not selected
        Assert.assertTrue(!layer.isSelectedProperty().get());

        layer.setSelected(false);

        //layer should be deselected now
        Assert.assertTrue(!layer.isSelectedProperty().get());

        //lerry, curly, and moe should all be deselected now that layer is deselected
        Assert.assertTrue(!lerry.isSelectedProperty().get());
        Assert.assertTrue(!curly.isSelectedProperty().get());
        Assert.assertTrue(!moe.isSelectedProperty().get());

    }



}