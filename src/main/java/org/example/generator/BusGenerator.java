package org.example.generator;

import org.example.models.Bus;


public class BusGenerator extends DataGenerator<Bus> {

    @Override
    public Bus generate() {
        int number = generateUniqueNumber();
        String model = "Model-" + (int) (Math.random() * 5 + 1);
        int mileage = (int) (Math.random() * 200000);
        return new Bus.BusBuilder().setNumber(number).setModel(model).setMileage(mileage).build();
    }
}