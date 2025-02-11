package org.example.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class DataWriter {
    public static void writeArrayToFile(Object[] array, String filePath) {
        File file = new File(filePath);
        try {
            if (!file.exists()) file.createNewFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                for (Object item : array) {
                    writer.write(item + " ");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Writing file error: " + e.getMessage());
        }
    }
}
