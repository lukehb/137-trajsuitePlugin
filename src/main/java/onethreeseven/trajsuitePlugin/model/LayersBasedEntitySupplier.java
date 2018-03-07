package onethreeseven.trajsuitePlugin.model;

import java.util.*;

/**
 * Supplies entities from the {@link Layers} object.
 * @author Luke Bermingham
 */
public class LayersBasedEntitySupplier implements EntitySupplier {


    @Override
    public <T> T supply(String id, String layername, Class<T> modelType) {
        return BaseTrajSuiteProgram.getInstance().getLayers().getEntity(layername, id, modelType).model;
    }

    @Override
    public <T> Collection<T> supply(String layername, Class<T> modelType) {
        Collection models = BaseTrajSuiteProgram.getInstance().getLayers().getLayerModels(layername);
        Collection<T> typedModels = new ArrayList<>();
        for (Object model : models) {
            if(model.getClass().equals(modelType)){
                typedModels.add((T) model);
            }
        }
        return typedModels;
    }

    @Override
    public <T> Collection<T> supply(Class<T> classType) {
        return BaseTrajSuiteProgram.getInstance().getLayers().getAllModelsOfType(classType);
    }

    @Override
    public Map<Class, Collection<Object>> supplyAllSelected() {

        Map<Class, Collection<Object>> selectedModels = new HashMap<>();

        Layers layers = BaseTrajSuiteProgram.getInstance().getLayers();

        for (WrappedEntityLayer layer : layers) {

            for (Object item : layer) {
                WrappedEntity wrappedEntity = (WrappedEntity) item;
                if (wrappedEntity.isSelectedProperty().get()) {
                    Class modelType = wrappedEntity.model.getClass();
                    Collection<Object> existingCollection = selectedModels.computeIfAbsent(modelType, k -> new ArrayList<>());
                    existingCollection.add(wrappedEntity.model);
                }
            }
        }

        return selectedModels;
    }

    @Override
    public void supplyAllSelectedByTypes(Map<Class, Collection<Object>> toPopulate) {

        Layers layers = BaseTrajSuiteProgram.getInstance().getLayers();

        for (WrappedEntityLayer layer : layers) {

            //now check if the entities in the layer are selected, and if so, accumulate them
            for (Object item : layer) {
                WrappedEntity wrappedEntity = (WrappedEntity) item;
                if (wrappedEntity.isSelectedProperty().get()) {

                    Class modelType = wrappedEntity.model.getClass();

                    //check whether the map has this type of model
                    Collection<Object> existingCollection = toPopulate.get(modelType);
                    if(existingCollection == null){
                        continue;
                    }

                    existingCollection.add(wrappedEntity.model);
                }
            }
        }

    }

}
