package org.example.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

public class DataLoader {

    public static <T> T[] loadFromFile(String filePath, int maxSize, Mapper<T> mapper, Class<T> clazz) {
        T[] result = (T[]) Array.newInstance(clazz, maxSize);
        int index = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (index >= maxSize) {
                    System.out.println("Max size reached");
                    break;
                }
                try {
                    result[index++] = mapper.map(line);
                } catch (Exception e) {
                    System.out.println("Line processing error: " + line + " (" + e.getMessage() + ")");
                }
            }
        } catch (IOException e) {
            System.out.println("Reading file error: " + e.getMessage());
        }

        return trimArray(result, index, clazz);
    }

    private static <T> T[] trimArray(T[] array, int size, Class<T> clazz) {
        T[] trimmed = (T[]) Array.newInstance(clazz, size);
        System.arraycopy(array, 0, trimmed, 0, size);
        return trimmed;
    }
}

