package onethreeseven.trajsuitePlugin.view;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;

import java.util.function.Function;

/**
 * Checks if all boolean properties in a map of some type T are the same.
 * @author Luke Bermingham
 */
public class AnyMatchingBooleanBinding<T> extends BooleanBinding {

    private final ObservableMap<?, T> map;
    private final boolean matchValue;
    private final Function<T, BooleanProperty> propertyEvaluator;

    public AnyMatchingBooleanBinding(boolean matchValue, ObservableMap<?, T> map, Function<T, BooleanProperty> propertyEvaluator) {

        this.map = map;
        this.matchValue = matchValue;
        this.propertyEvaluator = propertyEvaluator;

        map.addListener((MapChangeListener<Object, T>) change -> {
            refreshBinding();
        });

    }

    @Override
    protected boolean computeValue() {
        for (T entity : map.values()) {
            BooleanProperty property = propertyEvaluator.apply(entity);
            if(property.get() == matchValue){
                return true;
            }
        }
        return false;
    }

    private void refreshBinding() {
        //unbind all properties
        for (T entity : map.values()) {
            BooleanProperty property = propertyEvaluator.apply(entity);
            super.unbind(property);
        }

        //bind all properties
        for (T entity : map.values()) {
            BooleanProperty property = propertyEvaluator.apply(entity);
            super.bind(property);
        }
        //forces recalculation
        this.invalidate();
    }

}
