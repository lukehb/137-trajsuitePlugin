package onethreeseven.trajsuitePlugin.transaction;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * Essentially a thread-safe count-down timer where edits can be accumulated in this
 * object and after a certain time has passed then the total number of edits is consumed.
 * @author Luke Bermingham
 */
public class AccumulatingEditHistory {

    //concurrent fields
    private final AtomicInteger accumulatedEdits = new AtomicInteger(0);
    private final AtomicLong accumulatedTime = new AtomicLong(0);
    private final AtomicBoolean currentlyWorking = new AtomicBoolean(false);

    //normal fields
    private final long accumulationTotalMs;
    private final Consumer<Integer> onAccumulated;

    private final ThreadFactory threadFactory = r -> {
        Thread thread = new Thread(r);
        thread.setName("Accumulating Edit Transaction");
        return thread;
    };
    private final ExecutorService service = Executors.newSingleThreadExecutor(threadFactory);

    public AccumulatingEditHistory(long accumulationTotalMs, Consumer<Integer> onAccumulated){
        this.accumulationTotalMs = accumulationTotalMs;
        this.onAccumulated = onAccumulated;
    }

    public void accumulate(){
        //reset time when we accumulate new transaction
        accumulatedEdits.incrementAndGet();
        startWorking();
    }

    private void startWorking(){
        //already working
        if(currentlyWorking.get()){
            //reset accumulated time if we got another transaction
            accumulatedTime.set(0);
            return;
        }
        //start working
        currentlyWorking.set(true);

        CompletableFuture.runAsync(()->{

            long prevTime = System.currentTimeMillis();
            while(accumulatedTime.get() < accumulationTotalMs){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                long curTime = System.currentTimeMillis();
                long timeDelta = curTime - prevTime;
                prevTime = curTime;
                accumulatedTime.addAndGet(timeDelta);
            }

        }, service).thenRun(()->{
            //reset edits
            //pass in edits to consumer
            onAccumulated.accept(accumulatedEdits.getAndSet(0));
            currentlyWorking.set(false);
        });
    }



}
