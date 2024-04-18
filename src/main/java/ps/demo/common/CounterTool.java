package ps.demo.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class CounterTool {

    private final ConcurrentHashMap<String, AtomicLong> counterMap = new ConcurrentHashMap<>();

    private CounterTool() {}

    private static class CounterToolHolder {
        private static final CounterTool INSTANCE = new CounterTool();
    }

    public static CounterTool getInstance() {
        return CounterToolHolder.INSTANCE;
    }

    private AtomicLong getCounter(String key) {
        initCounter(key);
        return counterMap.get(key);
    }

    public long getCounterValue(String key) {
        initCounter(key);
        return counterMap.get(key).get();
    }

    private void initCounter(String key) {
        if (counterMap.containsKey(key)) {
            return;
        }
        synchronized (this) {
            if (counterMap.containsKey(key)) {
                return;
            }
            counterMap.put(key, new AtomicLong(0L));
        }
    }

    public long increment(String key) {
        return increment(key, 1);
    }

    public long increment(String key, int i) {
        return getCounter(key).addAndGet(i);

    }

    public long decrement(String key) {
        return increment(key,-1);
    }

    public boolean increasedMoreThenOnce(String key) {
        return getCounter(key).get() > 1L;
    }

    public void reset(String key) {
        getCounter(key).set(0L);
    }

}
