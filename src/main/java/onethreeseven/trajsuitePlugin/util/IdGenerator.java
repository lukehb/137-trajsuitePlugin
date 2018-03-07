package onethreeseven.trajsuitePlugin.util;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Id generator that generates reasonable sequential ids.
 * What is reasonable? Well, the ids have a clear "glanceable" progression
 * but they aren't just number, because that'd be ugly!
 * Also, this class is built for multi-threaded access.
 */
public final class IdGenerator {

    private static final String[] candidates = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    private static final AtomicInteger counter = new AtomicInteger(0);

    private IdGenerator() {
    }

    public static String nextId() {
        //accumulate it in the map if we don't have it already
        int idx = counter.getAndIncrement();

        StringBuilder idString = new StringBuilder();

        Integer scaledIdx = idx;
        while (scaledIdx >= candidates.length) {
            idString.append(candidates[scaledIdx % candidates.length]);

            scaledIdx = (scaledIdx / candidates.length) - 1;
        }

        idString.append(candidates[scaledIdx % candidates.length]);

        return idString.toString();
    }


}
