package ps.demo.common;

import java.util.HashMap;
import java.util.Map;

public class MapTool {

    private MapTool() {

    }

    public static <K, V> Map<K, V> of(Object ... keyValues) {
        final HashMap<K, V> result = new HashMap<>();
        for (int i = 0, n = keyValues.length; i < n; i += 2) {
            result.put((K)keyValues[i], (i+1 < keyValues.length) ? (V)keyValues[i+1] : null);
        }
        return result;
    }

}
