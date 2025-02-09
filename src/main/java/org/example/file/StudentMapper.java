package org.example.file;

import org.example.models.Student;

public class StudentMapper implements Mapper<Student> {
    @Override
    public Student map(String line) {
        String[] parts = line.split(",");
        return new Student.StudentBuilder()
            .setGroupNumber(Integer.parseInt(parts[0].trim()))
            .setAverageScore(Double.parseDouble(parts[1].trim()))
            .setRecordBookNumber(parts[2].trim())
            .build();
    }
}
