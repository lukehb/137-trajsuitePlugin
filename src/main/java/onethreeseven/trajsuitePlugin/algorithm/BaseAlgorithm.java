package onethreeseven.trajsuitePlugin.algorithm;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Base class for algorithm implementations.
 * @author Luke Bermingham
 */
public abstract class BaseAlgorithm<T, P extends BaseAlgorithmParams> {

    protected final AtomicBoolean isRunning = new AtomicBoolean(false);

    public T run(P params){
        isRunning.set(true);
        if(!params.areParametersValid()){
            isRunning.set(false);
            throw new IllegalArgumentException("Parameters are invalid, please pass valid parameters to: " + getSimpleName());
        }
        T res = runImpl(params);
        isRunning.set(false);
        return res;
    }

    public void stop(){
        isRunning.set(false);
    }

    protected abstract T runImpl(P params);

    public abstract String getSimpleName();

}
