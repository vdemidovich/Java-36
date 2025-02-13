package org.example.generator;

import org.example.models.Student;

public class StudentGenerator extends DataGenerator<Student> {
    @Override
    public Student generate() {
        int group = (int) (Math.random() * 5) + 1;
        double grade = Math.round((Math.random() * 9 + 1) * 10) / 10.0;
        int gradeBook = (int) (Math.random() * 200000);
        return new Student.StudentBuilder().setGroupNumber(group).setAverageScore(grade).setRecordBookNumber(gradeBook).build();
    }
}