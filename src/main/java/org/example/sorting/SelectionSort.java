package org.example.sorting;

import java.util.Comparator;

public class SelectionSort <T extends Comparable<T>> implements SortingStrategy<T> {

    @Override
    public void sort(T[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j].compareTo(array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            T temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }

    public void sort(T[] array, Comparator<T> comparator) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (comparator.compare(array[j], array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            T temp = array[minIndex];
            array[minIndex] = array[i];
            array[i] = temp;
        }
    }

    public void sort(T[] array, Comparator<T> comparator, SelectionSort.NumberExtractor<T> numberExtractor) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;

            if (Math.floor(numberExtractor.extractNumber(array[i]).doubleValue()) % 2 != 0) {
                continue;
            }

            for (int j = i + 1; j < array.length; j++) {
                if (Math.floor(numberExtractor.extractNumber(array[j]).doubleValue()) % 2 == 0
                        && comparator.compare(array[j], array[minIndex]) < 0) {
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
