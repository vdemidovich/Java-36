package org.example.sorting.strategies.bus;

import org.example.models.Bus;
import org.example.sorting.SelectionSort;
import org.example.sorting.SortingStrategy;

public class BusSortByModel implements SortingStrategy<Bus> {
    @Override
    public void sort(Bus[] array) {
        SelectionSort<Bus> selectionSort = new SelectionSort<>();
        Bus.BusModelComparator comparator = new Bus.BusModelComparator();
        selectionSort.sort(array, comparator);
    }
}