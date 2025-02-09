package org.example.sorting;

public interface SortingStrategy <T extends Comparable<T>> {
    void sort(T[] array);
}
