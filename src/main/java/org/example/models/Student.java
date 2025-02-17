package org.example.models;

import org.example.file.Writable;

import java.util.Comparator;

public class Student implements Sortable<Student>, Writable {
    private final int groupNumber;
    private final double averageScore;
    private final int recordBookNumber;

    private Student(StudentBuilder builder) {
        this.groupNumber = builder.groupNumber;
        this.averageScore = builder.averageScore;
        this.recordBookNumber = builder.recordBookNumber;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public int getRecordBookNumber() {
        return recordBookNumber;
    }

    @Override
    public String toString() {
        return "Student [groupNumber=" + groupNumber + ", averageScore=" + averageScore +
                ", recordBookNumber=" + recordBookNumber + "]";
    }

    @Override
    public String toWriteFormat() {
        return groupNumber + ", " + averageScore + ", " + recordBookNumber;
    }

    @Override
    public int compareTo(Student o) {
        int result = Integer.compare(this.groupNumber, o.groupNumber);
        if (result == 0) {
            result = Double.compare(this.averageScore, o.averageScore);
        }
        if (result == 0) {
            result = Integer.compare(this.recordBookNumber, o.recordBookNumber);
        }
        return result;
    }

    public static class StudentGroupNumberComparator implements Comparator<Student> {
        @Override
        public int compare(Student student1, Student student2) {
            return Integer.compare(student1.getGroupNumber(), student2.getGroupNumber());
        }
    }

    public static class StudentAverageScoreComparator implements Comparator<Student> {
        @Override
        public int compare(Student student1, Student student2) {
            return Double.compare(student1.getAverageScore(), student2.getAverageScore());
        }
    }

    public static class StudentRecordBookNumberComparator implements Comparator<Student> {
        @Override
        public int compare(Student student1, Student student2) {
            return Integer.compare(student1.getRecordBookNumber(), student2.getRecordBookNumber());
        }
    }


    public static class StudentBuilder {
        private int groupNumber;
        private double averageScore;
        private int recordBookNumber;

        public StudentBuilder setGroupNumber(int groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public StudentBuilder setAverageScore(double averageScore) {
            this.averageScore = averageScore;
            return this;
        }

        public StudentBuilder setRecordBookNumber(int recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}
