package org.example.UI;

import org.example.file.*;
import org.example.generator.BusGenerator;
import org.example.generator.StudentGenerator;
import org.example.generator.UserGenerator;
import org.example.models.Bus;
import org.example.models.Student;
import org.example.models.User;
import org.example.sorting.SelectionSort;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class DataManagementApp extends JFrame {

    private static final String[] DATA_SOURCES = {"File", "Manual Input", "Random"};
    private static final String[] DATA_TYPES = {"Bus", "User", "Student"};

    private Object[] data;
    private JTextArea outputArea;
    private JComboBox<String> sortFieldComboBox;
    private JComboBox<String> dataSourceComboBox;
    private JComboBox<String> dataTypeComboBox;
    private JComboBox<String> sortParameterComboBox;
    private JTextField arraySizeField; // Поле для ввода длины массива

    public DataManagementApp() {
        data = new Object[0];
        setTitle("Data Management Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(6, 2)); // Увеличили количество строк для нового элемента

        JButton sortButton = new JButton("Sort");
        JButton searchButton = new JButton("Search");
        JButton inputButton = new JButton("Input Data");
        JButton exitButton = new JButton("Exit");

        dataSourceComboBox = new JComboBox<>(DATA_SOURCES);
        dataTypeComboBox = new JComboBox<>(DATA_TYPES);
        sortParameterComboBox = new JComboBox<>();
        arraySizeField = new JTextField("10"); // По умолчанию длина массива 10

        dataTypeComboBox.addActionListener(e -> updateSortParameters());

        controlPanel.add(sortButton);
        controlPanel.add(sortParameterComboBox);
        controlPanel.add(searchButton);
        controlPanel.add(dataSourceComboBox);
        controlPanel.add(inputButton);
        controlPanel.add(dataTypeComboBox);
        controlPanel.add(new JLabel("Array Size:"));
        controlPanel.add(arraySizeField);
        controlPanel.add(exitButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        sortButton.addActionListener(e -> sortData());
        searchButton.addActionListener(e -> searchData());
        inputButton.addActionListener(e -> inputData());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void updateSortParameters() {
        String dataType = (String) dataTypeComboBox.getSelectedItem();
        String[] parameters;
        data = new Object[0];

        switch (dataType) {
            case "Bus":
                parameters = new String[]{"Number", "Model", "Mileage"};
                break;
            case "User":
                parameters = new String[]{"Name", "Password", "Email"};
                break;
            case "Student":
                parameters = new String[]{"Group Number", "Average Grade", "Record Book Number"};
                break;
            default:
                parameters = new String[]{};
                break;
        }

        sortParameterComboBox.setModel(new DefaultComboBoxModel<>(parameters));
    }

    private Object[] resizeArray(Object[] array, int newSize) {
        return Arrays.copyOf(array, newSize);
    }

    private void addToArray(Object element) {
        data = resizeArray(data, data.length + 1);
        data[data.length - 1] = element;
    }

    private int getArraySize() {
        try {
            return Integer.parseInt(arraySizeField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid array size! Using default size 10.", "Error", JOptionPane.ERROR_MESSAGE);
            return 10; // Возвращаем значение по умолчанию
        }
    }

    private void sortData() {
        if (data.length == 0) {
            JOptionPane.showMessageDialog(this, "No data to sort.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dataType = (String) dataTypeComboBox.getSelectedItem();
        String parameter = (String) sortParameterComboBox.getSelectedItem();

        switch (dataType) {
            case "Bus":
                Bus[] buses = Arrays.copyOf(data, data.length, Bus[].class);
                SelectionSort<Bus> busSorter = new SelectionSort<>();
                busSorter.sort(buses); // сортировка по умолчанию(все 3 поля)
                data = buses;
                break;
            case "User":
                User[] users = Arrays.copyOf(data, data.length, User[].class);
                SelectionSort<User> userSorter = new SelectionSort<>();
                userSorter.sort(users);
                data = users;
                break;
            case "Student":
                Student[] students = Arrays.copyOf(data, data.length, Student[].class);
                SelectionSort<Student> studentSorter = new SelectionSort<>();
                studentSorter.sort(students);
                data = students;
                break;
        }

        writeToFile(data);
        displayData();
    }

    private void searchData() {
        String searchTerm = JOptionPane.showInputDialog(this, "Enter search term:");
        if (searchTerm != null && !searchTerm.isEmpty()) {
            Object[] results = Arrays.stream(data)
                    .filter(s -> s.toString().toLowerCase().contains(searchTerm.toLowerCase()))
                    .toArray();
            displaySearchResults(results);
            writeToFile(results);
        }
    }

    private void inputData() {
        String source = (String) dataSourceComboBox.getSelectedItem();
        String dataType = (String) dataTypeComboBox.getSelectedItem();
        int arraySize = getArraySize(); // Получаем длину массива

        switch (source) {
            case "File":
                loadFromFile(arraySize);
                break;
            case "Manual Input":
                manualInput(dataType, arraySize);
                break;
            case "Random":
                generateRandomData(dataType, arraySize);
                break;
        }
    }

    private void loadFromFile(int maxSize) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                data = new Object[0]; // Очистка массива
                String dataType = (String) dataTypeComboBox.getSelectedItem();

                if (dataType == null) {
                    JOptionPane.showMessageDialog(this, "Please select a valid data type.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Object[] loadedData = loadDataForType(selectedFile.getAbsolutePath(), dataType, maxSize);
                if (loadedData != null) {
                    data = loadedData;
                    displayData();
                }

                displayData();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void writeToFile(Object[] data) {
        DataWriter.writeArrayToFile(data, "src/main/java/org/example/file/results.txt");
    }

    private Object[] loadDataForType(String filePath, String dataType, int maxSize) {
        switch (dataType) {
            case "Bus":
                Bus[] buses = DataLoader.loadFromFile(filePath, maxSize, new BusMapper(), Bus.class);
                return buses;

            case "User":
                User[] users = DataLoader.loadFromFile(filePath, maxSize, new UserMapper(), User.class);
                return users;

            case "Student":
                Student[] students = DataLoader.loadFromFile(filePath, maxSize, new StudentMapper(), Student.class);
                return students;

            default:
                JOptionPane.showMessageDialog(this, "Unsupported data type: " + dataType, "Error", JOptionPane.ERROR_MESSAGE);
                return null;
        }
    }

    private void manualInput(String dataType, int arraySize) {
        switch (dataType) {
            case "Bus":
                try {
                    for (int i = 0; i < arraySize; i++) {
                        int busNumber = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Bus Number:"));
                        String busModel = JOptionPane.showInputDialog(this, "Enter Bus Model:");
                        double busMileage = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Bus Mileage:"));
                        addToArray(new Bus.BusBuilder()
                                .setNumber(busNumber)
                                .setModel(busModel)
                                .setMileage(busMileage)
                                .build());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "User":
                for (int i = 0; i < arraySize; i++) {
                    String userName = JOptionPane.showInputDialog(this, "Enter User Name:");
                    String userPassword = JOptionPane.showInputDialog(this, "Enter User Password:");
                    String userEmail = JOptionPane.showInputDialog(this, "Enter User Email:");
                    addToArray(new User.UserBuilder()
                            .setName(userName)
                            .setPassword(userPassword)
                            .setEmail(userEmail)
                            .build());
                }
                break;
            case "Student":
                try {
                    for (int i = 0; i < arraySize; i++) {
                        int groupNumber = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Group Number:"));
                        double averageGrade = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Average Grade:"));
                        String recordBookNumber = JOptionPane.showInputDialog(this, "Enter Record Book Number:"));
                        addToArray(new Student.StudentBuilder()
                                .setGroupNumber(groupNumber)
                                .setAverageScore(averageGrade)
                                .setRecordBookNumber(recordBookNumber)
                                .build());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;
        }
        displayData();
    }

    private void generateRandomData(String dataType, int arraySize) {
        Random random = new Random();
        switch (dataType) {
            case "Bus":
                BusGenerator busGenerator = new BusGenerator();
                for (int i = 0; i < arraySize; i++) {
                    addToArray(busGenerator.generate());
                }
                break;
            case "User":
                UserGenerator userGenerator = new UserGenerator();
                for (int i = 0; i < arraySize; i++) {
                    addToArray(userGenerator.generate());
                }
                break;
            case "Student":
                StudentGenerator studentGenerator = new StudentGenerator();
                for (int i = 0; i < arraySize; i++) {
                    addToArray(studentGenerator.generate());
                }
                break;
        }
        displayData();
    }

    private void displayData() {
        outputArea.setText("");
        for (Object item : data) {
            outputArea.append(item.toString() + "\n");
        }
    }

    private void displaySearchResults(Object[] results) {
        outputArea.setText("");
        for (Object item : results) {
            outputArea.append(item.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataManagementApp app = new DataManagementApp();
            app.setVisible(true);
        });
    }
}
