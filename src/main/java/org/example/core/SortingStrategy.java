package org.example.core;

public interface SortingStrategy <T extends Comparable<T>> {
    void sort(T[] array);
}
