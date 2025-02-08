package org.example.file;

import org.example.models.Student;

public class DataExample {
    public static void main(String[] args) {
        Student[] students = DataLoader.loadFromFile("src/main/java/org/example/file/students.txt", 5, new StudentMapper(), Student.class);
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
