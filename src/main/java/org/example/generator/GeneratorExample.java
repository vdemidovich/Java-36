package org.example.generator;

public class GeneratorExample {
    public static void main(String[] args) {
        BusGenerator bg = new BusGenerator();
        System.out.println(bg.generate());

        StudentGenerator sg = new StudentGenerator();
        System.out.println(sg.generate());
        System.out.println(sg.generate());

        UserGenerator ug = new UserGenerator();
        System.out.println(ug.generate());
    }
}
