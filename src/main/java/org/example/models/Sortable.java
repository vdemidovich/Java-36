package org.example.models;

public interface Sortable<T> extends Comparable<T> {
    @Override
    int compareTo(T o);
}
