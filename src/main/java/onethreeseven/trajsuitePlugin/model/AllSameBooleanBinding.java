package onethreeseven.trajsuitePlugin.model;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.function.Function;

/**
 * Checks if all boolean properties in a map of some type T are the same.
 * @author Luke Bermingham
 */
public class AllSameBooleanBinding<T> extends BooleanBinding {

    private final ObservableMap<?, T> map;
    private final boolean sameValue;
    private final Function<T, BooleanProperty> propertyEvaluator;

    public AllSameBooleanBinding(boolean sameValue, ObservableMap<?, T> map, Function<T, BooleanProperty> propertyEvaluator) {

        this.map = map;
        this.sameValue = sameValue;
        this.propertyEvaluator = propertyEvaluator;

        map.addListener((MapChangeListener<Object, T>) change -> {
            refreshBinding();
        });

    }

    @Override
    protected boolean computeValue() {
        for (T entity : map.values()) {
            BooleanProperty property = propertyEvaluator.apply(entity);
            if(property.get() != sameValue){
                return false;
            }
        }
        return true;
    }

    private void refreshBinding() {
        //unbind all properties
        for (T entity : map.values()) {
            super.unbind(propertyEvaluator.apply(entity));
        }

        //bind all properties
        for (T entity : map.values()) {
            super.bind(propertyEvaluator.apply(entity));
        }
        //forces recalculation
        this.invalidate();
    }

}
