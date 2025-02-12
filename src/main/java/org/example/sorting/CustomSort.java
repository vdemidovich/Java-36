package org.example.sorting;

import java.util.Comparator;

public class CustomSort<T> {
    public void sortWithEvenLogic(T[] array, Comparator<T> comparator, NumberExtractor<T> numberExtractor) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;

            if (numberExtractor.extractNumber(array[i]).doubleValue() % 2 != 0) {
                continue;
            }

            for (int j = i + 1; j < array.length; j++) {
                if (numberExtractor.extractNumber(array[j]).doubleValue() % 2 == 0 && comparator.compare(array[j], array[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                T temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
            }
        }
    }

    public interface NumberExtractor<T> {
        Number extractNumber(T object);
    }
}
