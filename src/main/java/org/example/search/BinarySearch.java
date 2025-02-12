package org.example.search;

import java.util.Comparator;

public class BinarySearch {

    public static <T> int rank  (T key, T [ ] a, Comparator<T> comparator) {
        int min = 0;
        int max = a.length - 1;
        while (min <= max) {
            int mid = ((max - min) / 2) + min;
            if (comparator.compare(key, a [mid]) > 0)
                min = mid + 1;
            else if (comparator.compare (key, a[mid]) < 0)
                 max = mid - 1;
            else if (comparator.compare (key, a[mid] )==0) {
                return mid;
            }
        }
        return -1;
    }
}


