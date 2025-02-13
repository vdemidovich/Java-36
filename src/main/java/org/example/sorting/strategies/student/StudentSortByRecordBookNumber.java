package org.example.sorting.strategies.student;

import org.example.models.Student;
import org.example.sorting.SelectionSort;
import org.example.sorting.SortingStrategy;

public class StudentSortByRecordBookNumber implements SortingStrategy<Student> {
    @Override
    public void sort(Student[] array) {
        SelectionSort<Student> selectionSort = new SelectionSort<>();
        selectionSort.sort(array, new Student.StudentRecordBookNumberComparator());
    }
}
