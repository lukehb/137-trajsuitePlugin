package onethreeseven.trajsuitePlugin.view;

import onethreeseven.trajsuitePlugin.model.BaseTrajSuiteProgram;
import onethreeseven.trajsuitePlugin.model.JavaFXThreadingRule;
import onethreeseven.trajsuitePlugin.model.Layers;
import onethreeseven.trajsuitePlugin.model.LayersBasedEntitySupplier;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * Testing the widget that is populated with entities using a service loader.
 * @author Luke Bermingham
 */
public class EntitySelectorTest  {

    //use gradle test runner, intellij seems to not include necessary modules on testing

//    @Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
//    @Test
//    public void testPopulateWithEntities(){
//
//        Layers layers = BaseTrajSuiteProgram.getInstance().getLayers();
//
//        String[] strToAdd = new String[]{
//              "test",
//              "yo",
//              "hey"
//        };
//
//        for (String entityToAdd : strToAdd) {
//            layers.add(entityToAdd);
//        }
//
//        EntitySelector<String> entitySelector = new EntitySelector<>(String.class);
//
//        Assert.assertTrue(entitySelector.getItems().size() == strToAdd.length);
//
//        for (int i = 0; i < strToAdd.length; i++) {
//            Assert.assertTrue(strToAdd[i].equals(entitySelector.getItems().get(i)));
//        }
//
//    }

}