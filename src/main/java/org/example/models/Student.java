package org.example.models;

import java.util.Comparator;

public class Student implements Comparable<Student> {
    private final int groupNumber;
    private final double averageScore;
    private final String recordBookNumber;

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

    public String getRecordBookNumber() {
        return recordBookNumber;
    }

    @Override
    public String toString() {
        return "Student [groupNumber=" + groupNumber + ", averageScore=" + averageScore +
                ", recordBookNumber=" + recordBookNumber + "]";
    }

    @Override
    public int compareTo(Student o) {
        return Double.compare(this.averageScore, o.averageScore);
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
            return student1.getRecordBookNumber().compareTo(student2.getRecordBookNumber());
        }
    }


    public static class StudentBuilder {
        private int groupNumber;
        private double averageScore;
        private String recordBookNumber;

        public StudentBuilder setGroupNumber(int groupNumber) {
            this.groupNumber = groupNumber;
            return this;
        }

        public StudentBuilder setAverageScore(double averageScore) {
            this.averageScore = averageScore;
            return this;
        }

        public StudentBuilder setRecordBookNumber(String recordBookNumber) {
            this.recordBookNumber = recordBookNumber;
            return this;
        }

        public Student build() {
            if (this.groupNumber < 1) {
                throw new IllegalArgumentException("Group number cannot be < 1");
            }
            if (this.recordBookNumber == null || this.recordBookNumber.isEmpty()) {
                throw new IllegalArgumentException("RecordBookNumber cannot be null or empty");
            }
            if (this.averageScore < 0) {
                throw new IllegalArgumentException("AverageScore cannot be negative");
            }
            return new Student(this);
        }
    }
}
