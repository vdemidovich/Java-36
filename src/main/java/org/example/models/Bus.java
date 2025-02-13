package org.example.models;

import org.example.file.Writable;

import java.util.Comparator;

public class Bus implements Sortable<Bus>, Writable {
    private final int number;
    private final String model;
    private final double mileage;

    private Bus(BusBuilder builder) {
        this.number = builder.number;
        this.model = builder.model;
        this.mileage = builder.mileage;
    }

    public int getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public double getMileage() {
        return mileage;
    }

    @Override
    public String toString() {
        return "Bus [number=" + number + ", model=" + model + ", mileage=" + mileage + "]";
    }

    @Override
    public int compareTo(Bus other) {
        int result = Integer.compare(this.number, other.number);
        if (result == 0) {
            result = this.model.compareTo(other.model);
        }
        if (result == 0) {
            result = Double.compare(this.mileage, other.mileage);
        }
        return result;
    }

    @Override
    public String toWriteFormat() {
        return number + ", " + model + ", " + mileage;
    }

    public static class BusNumberComparator implements Comparator<Bus> {
        @Override
        public int compare(Bus bus1, Bus bus2) {
            return Integer.compare(bus1.getNumber(), bus2.getNumber());
        }
    }

    public static class BusModelComparator implements Comparator<Bus> {
        @Override
        public int compare(Bus bus1, Bus bus2) {
            return bus1.getModel().compareTo(bus2.getModel());
        }
    }

    public static class BusMileageComparator implements Comparator<Bus> {
        @Override
        public int compare(Bus bus1, Bus bus2) {
            return Double.compare(bus1.getMileage(), bus2.getMileage());
        }
    }


    public static class BusBuilder {
        private int number;
        private String model;
        private double mileage;

        public BusBuilder setNumber(int number) {
            this.number = number;
            return this;
        }

        public BusBuilder setModel(String model) {
            this.model = model;
            return this;
        }

        public BusBuilder setMileage(double mileage) {
            this.mileage = mileage;
            return this;
        }

        public Bus build() {
            return new Bus(this);
        }
    }
}
