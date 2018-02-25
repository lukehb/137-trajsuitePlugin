package onethreeseven.trajsuitePlugin.model;


import javafx.beans.InvalidationListener;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.HashMap;
import java.util.Iterator;

/**
 * A layer of {@link Entity}
 * @author Luke Bermingham
 */
public class EntityLayer<T extends Entity> implements Selectable, Iterable<T> {

    protected final ObservableMap<String, T> entities;
    private final Class modelType;
    private final SimpleBooleanProperty isSelected;
    private String layerName;

    public EntityLayer(String layerName, Class modelType){
        this.entities = FXCollections.observableMap(new HashMap<>());
        this.modelType = modelType;
        this.isSelected = new ReadOnlyBooleanWrapper(false);
        this.layerName = layerName;

        //setup selection change listener, so when layer changes, so do the children
        isSelected.addListener((observable, oldValue, newValue) -> {
            for (Entity entity : EntityLayer.this) {
                entity.isSelectedProperty().setValue(newValue);
            }
        });

        //setup binding so layer selection is always determined by children
        isSelected.bind(new IsLayerSelectedBinding().asObject());

    }

    public void add(T entity){
        entities.put(entity.getId(), entity);
    }

    public Entity get(String entityId){
        return this.entities.get(entityId);
    }


    public int size(){
        return entities.size();
    }

    @Override
    public Iterator<T> iterator() {
        return entities.values().iterator();
    }

    public String getLayerName() {
        return layerName;
    }

    public T remove(String id){
        return this.entities.remove(id);
    }

    @Override
    public String toString() {
        return layerName;
    }

    public Class<T> getModelType() {
        return modelType;
    }

    @Override
    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }

    public void setSelected(boolean allSelected){
        for (T entity : entities.values()) {
            entity.isSelectedProperty().set(allSelected);
        }
    }


    private class IsLayerSelectedBinding extends BooleanBinding{

        IsLayerSelectedBinding(){
            EntityLayer.this.entities.addListener((MapChangeListener<String, T>) change -> refreshBinding());
        }

        @Override
        protected boolean computeValue() {
            boolean isVisible = true;
            for (T entity : entities.values()) {
                if(!entity.isSelectedProperty().get()){
                    isVisible = false;
                    break;
                }
            }
            return isVisible;
        }

        private void refreshBinding() {
            //unbind all selected properties
            for (T entity : entities.values()) {
                super.unbind(entity.isSelectedProperty());
            }

            //bind all selected properties
            for (T entity : entities.values()) {
                super.bind(entity.isSelectedProperty());
            }
            //forces recalculation
            this.invalidate();
        }


    }

}
