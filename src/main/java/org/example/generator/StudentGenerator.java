package org.example.generator;

import org.example.models.Student;

public class StudentGenerator extends DataGenerator<Student> {
    @Override
    public Student generate() {
        int group = (int) (Math.random() * 100);
        double grade = Math.round(Math.random() * 100) / 10.0;
        String gradeBook = String.valueOf(generateUniqueNumber());
        return new Student.StudentBuilder().setGroupNumber(group).setAverageScore(grade).setRecordBookNumber(gradeBook).build();
    }
}
