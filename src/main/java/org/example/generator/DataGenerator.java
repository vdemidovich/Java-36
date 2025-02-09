package org.example.generator;

abstract class DataGenerator<T> {
    protected int[] uniqueNumbers = new int[1000];
    protected int count = 0;

    public abstract T generate();

    protected int generateUniqueNumber() {
        int number;

        do {
            number = (int) (Math.random() * 1000);
        }
        while (contains(number));

        uniqueNumbers[count++] = number;
        return number;
    }

    private boolean contains(int number) {
        for (int i = 0; i < count; i++) {
            if (uniqueNumbers[i] == number) {
                return true;
            }
        }
        return false;
    }
}

