package main.service;

import main.domain.Keyable;

import java.io.Serializable;
import java.util.List;

public class BinarySearch {

    public static <K extends Comparable<K>, T extends Serializable & Keyable<K>> int binarySearch(K key, List<T> array) {
        int low = 0;
        int high = array.size() - 1;
        int result = 0;
        while (low <= high) {
            int mid = low + ((high - low) / 2);
             result = key.compareTo(array.get(mid).getKey());
            if (result == 0)
                return 0;
            if (result > 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return result;
    }
}
