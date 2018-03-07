package onethreeseven.trajsuitePlugin.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import onethreeseven.trajsuitePlugin.view.AnyMatchingBooleanBinding;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * A layer of {@link WrappedEntity}
 * @author Luke Bermingham
 */
public class WrappedEntityLayer implements Selectable, Iterable<WrappedEntity> {

    protected final ObservableMap<String, WrappedEntity> entities;
    protected final String layerName;
    protected final ReadOnlyBooleanWrapper isSelected;

    public WrappedEntityLayer(String layerName) {
        this(layerName, null);
    }

    public WrappedEntityLayer(String layerName, Map<String, WrappedEntity> toInsert){
        this.layerName = layerName;
        this.entities = FXCollections.observableMap(initMap());
        if(toInsert != null){
            this.entities.putAll(toInsert);
        }
        this.isSelected = new ReadOnlyBooleanWrapper(false);

        //setup binding so layer selection is always determined by children
        isSelected.bind(new AnyMatchingBooleanBinding<>(true, entities, WrappedEntity::isSelectedProperty));
    }

    /**
     * Implementors may wish to use other map types, such as concurrent map.
     * @return The map that the entities in this layer will be stored in.
     */
    protected Map<String, WrappedEntity> initMap(){
        return new ConcurrentHashMap<>();
    }

    public WrappedEntity get(String id){
        return this.entities.get(id);
    }

    public void add(WrappedEntity entity){
        entities.put(entity.getId(), entity);
    }

    public int size(){
        return entities.size();
    }

    public Iterator<WrappedEntity> iterator() {
        return entities.values().iterator();
    }

    public String getLayerName() {
        return layerName;
    }

    public WrappedEntity remove(String id){
        return this.entities.remove(id);
    }

    @Override
    public String toString() {
        return layerName;
    }

    @Override
    public ReadOnlyBooleanProperty isSelectedProperty() {
        return isSelected.getReadOnlyProperty();
    }

    public void selectAll(boolean doSelect){
        for (WrappedEntity entity : entities.values()) {
            entity.isSelectedProperty().set(doSelect);
        }
    }

}
