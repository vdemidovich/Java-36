package org.example.file;

import org.example.models.Bus;

public class BusMapper implements Mapper<Bus> {
    @Override
    public Bus map(String line) {
        String[] parts = line.split(",");
        return new Bus.BusBuilder()
            .setNumber(Integer.parseInt(parts[0].trim()))
            .setModel(parts[1].trim())
            .setMileage(Double.parseDouble(parts[2].trim()))
            .build();
    }
}

