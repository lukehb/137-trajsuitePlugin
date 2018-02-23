package onethreeseven.trajsuitePlugin.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Supplies entities from the {@link Layers} object.
 * @author Luke Bermingham
 */
public class LayersBasedEntitySupplier implements EntitySupplier {

    private static Layers layers;

    @Override
    public <T> Collection<T> supply(Class<T> classType) {
        if(layers == null){
            return null;
        }
        WrappedEntityLayer<T> layer = layers.getLayerByType(classType);
        Collection<T> models = new ArrayList<>();
        for (WrappedEntity<T> wrappedEntity : layer) {
            models.add(wrappedEntity.model);
        }
        return models;
    }

    public static void setLayers(Layers layers) {
        LayersBasedEntitySupplier.layers = layers;
    }
}
