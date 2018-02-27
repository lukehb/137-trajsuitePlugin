package onethreeseven.trajsuitePlugin.model;

import java.util.*;

/**
 * Supplies entities from the {@link Layers} object.
 * @author Luke Bermingham
 */
public class LayersBasedEntitySupplier implements EntitySupplier {

    private static Layers layers;

    @Override
    public <T> T supply(String id, String layername, Class<T> modelType) {
        if(layers == null){
            return null;
        }
        return layers.getEntity(layername, id, modelType).model;
    }

    @Override
    public <T> Collection<T> supply(String layername, Class<T> modelType) {
        if(layers == null){
            return null;
        }
        return layers.getLayerModels(layername, modelType);
    }

    @Override
    public <T> Collection<T> supply(Class<T> classType) {
        if(layers == null){
            return null;
        }
        return layers.getAllModelsOfType(classType);
    }

    @Override
    public Map<Class, Collection<Object>> supplyAllSelected() {

        Map<Class, Collection<Object>> selectedModels = new HashMap<>();

        if(layers != null){
            for (WrappedEntityLayer layer : layers) {

                Class modelType = layer.getModelType();
                Collection<Object> existingCollection = selectedModels.computeIfAbsent(modelType, k -> new ArrayList<>());

                for (Object item : layer) {
                    WrappedEntity wrappedEntity = (WrappedEntity) item;
                    if (wrappedEntity.isSelectedProperty().get()) {
                        existingCollection.add(wrappedEntity.model);
                    }
                }
            }
        }
        return selectedModels;
    }

    @Override
    public void supplyAllSelectedByTypes(Map<Class, Collection<Object>> toPopulate) {

        if(layers != null){
            for (WrappedEntityLayer layer : layers) {

                Class modelType = layer.getModelType();

                //check whether the map has this type of model
                Collection<Object> existingCollection = toPopulate.get(modelType);
                if(existingCollection == null){
                    continue;
                }

                //now check if the entities in the layer are selected, and if so, add them
                for (Object item : layer) {
                    WrappedEntity wrappedEntity = (WrappedEntity) item;
                    if (wrappedEntity.isSelectedProperty().get()) {
                        existingCollection.add(wrappedEntity.model);
                    }
                }
            }
        }

    }

    public static void setLayers(Layers layers) {
        LayersBasedEntitySupplier.layers = layers;
    }
}
