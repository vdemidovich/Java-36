package org.example;

import org.example.core.SelectionSort;
import org.example.models.Bus;
import org.example.models.Student;
import org.example.models.User;

public class Main {
    public static void main(String[] args) {

        Bus bus1 = new Bus.BusBuilder().setNumber(102).setModel("Model1").setMileage(120000).build();
        Bus bus2 = new Bus.BusBuilder().setNumber(101).setModel("Model2").setMileage(80000).build();
        Bus bus3 = new Bus.BusBuilder().setNumber(104).setModel("Model3").setMileage(150000).build();
        Bus bus4 = new Bus.BusBuilder().setNumber(103).setModel("Model2").setMileage(50000).build();

        Bus[] buses = {bus1, bus2, bus3, bus4};

        System.out.println("Before sorting:");
        for (Bus bus : buses) {
            System.out.println(bus);
        }

        SelectionSort<Bus> selectionSort = new SelectionSort<>();
        selectionSort.sort(buses);

        System.out.println("\nAfter sorting:");
        for (Bus bus : buses) {
            System.out.println(bus);
        }

        Bus.BusMileageComparator comparator = new Bus.BusMileageComparator();
        selectionSort.sort(buses, comparator);

        System.out.println("\nAfter sorting by Bus BusMileageComparator:");
        for (Bus bus : buses) {
            System.out.println(bus);
        }

        Bus.BusModelComparator comparator2 = new Bus.BusModelComparator();
        selectionSort.sort(buses, comparator2);

        System.out.println("\nAfter sorting by Bus BusModelComparator:");
        for (Bus bus : buses) {
            System.out.println(bus);
        }
    }

}