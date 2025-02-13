package org.example.sorting.strategies.user;

import org.example.models.User;
import org.example.sorting.SelectionSort;
import org.example.sorting.SortingStrategy;

public class UserSortByEmail implements SortingStrategy<User> {
    @Override
    public void sort(User[] array) {
        SelectionSort<User> selectionSort = new SelectionSort<>();
        selectionSort.sort(array, new User.UserEmailComparator());
    }
}
