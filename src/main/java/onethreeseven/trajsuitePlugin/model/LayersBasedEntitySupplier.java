package onethreeseven.trajsuitePlugin.model;

import java.util.*;

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

    @Override
    public Map<String, Object> supplyAllSelected() {

        Map<String, Object> selectedEntities = new HashMap<>();

        if(layers != null){
            for (WrappedEntityLayer layer : layers) {
                for (Object item : layer) {
                    WrappedEntity wrappedEntity = (WrappedEntity) item;
                    if (wrappedEntity.isSelected()) {
                        selectedEntities.put(wrappedEntity.getId(), wrappedEntity.model);
                    }
                }
            }
        }
        return selectedEntities;
    }

    public static void setLayers(Layers layers) {
        LayersBasedEntitySupplier.layers = layers;
    }
}
