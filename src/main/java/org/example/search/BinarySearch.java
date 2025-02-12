package org.example.search;

import org.example.models.Bus;
import org.example.sorting.SelectionSort;

public class BinarySearch {

    public static <T> int rank  (T key, T [ ] a, Comparator <T>  comparator) {
        int min = 0;
        int max = a.length - 1;
        int count = 0 ;
        while (min <= max) {
            int mid = ((max - min) / 2) + min;
            if (comparator.compare(key, a [mid]) > 0)
                min = mid + 1;
            else if (comparator.compare (key, a[mid]) < 0)
                 max = mid - 1;
            else if (comparator.compare (key, a[mid] )==0) {
                return mid;
            }
            count ++;
        }
        return -1;
    }
    public static void main (String [] args) { Bus[ ] buses = {
                new Bus.BusBuilder() .setNumber(123).setModel("Volvo"). setMileage(500000).build(),
            new Bus.BusBuilder().setNumber(789).setModel("Mercedes").setMileage(30000).build(),
             new Bus.BusBuilder().setNumber(456).setModel("Iveco"). setMileage(15000).build(),
            new Bus.BusBuilder() .setNumber(749).setModel("Mercedes"). setMileage(300000).build(),
            new Bus.BusBuilder() .setNumber(366).setModel("Iveco"). setMileage(15000).build()};

            SelectionSort<Bus>sorter =new SelectionSort<>();
                    sorter.sort(buses, new Bus.BusNumberComparator());
        int result =BinarySearch.rank (new Bus.BusBuilder().setNumber(456).build(), buses,new Bus.BusNumberComparator());
        System.out.println(buses[result]);
        }
    }


