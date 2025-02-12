package org.example.search;

public class BinarySearch {

    public static int rank (int key, int [ ] a) {
        int min = 0;
        int max = a.length - 1;
        int count = 0 ;
        while (min <= max) {
            int mid = ((max - min) / 2) + min;
            if (key > a [mid])
                min = mid -1;
            else if (key ==a[mid]) {
                return mid;
            }
            count ++;
        }
        return -1;
    }
    public static void main (String [] args) {
        int data [ ] = { 3, 6, 7, 10, 34, 56, 60 } ;
        int numberToFind = 10;
        int answer  = rank (numberToFind, data);
        if (answer >= 0) {
            System.out.println( answer);
        } else {
            System.out.println("Не найдено");
        }
    }
}

