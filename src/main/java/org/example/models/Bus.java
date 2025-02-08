package org.example.models;

import java.util.Comparator;

public class Bus implements Comparable<Bus> {
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
    public int compareTo(Bus o) {
        return this.number - o.number;
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
            if (this.number < 0) {
                throw new IllegalArgumentException("Number cannot be negative");
            }
            if (this.model == null || this.model.isEmpty()) {
                throw new IllegalArgumentException("Model cannot be null or empty");
            }
            if (this.mileage < 0) {
                throw new IllegalArgumentException("Mileage cannot be negative");
            }
            return new Bus(this);
        }
    }
}
