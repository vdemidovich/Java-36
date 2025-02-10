package main.java.org.example.UI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class DataManagementApp extends JFrame {
    private Object[] data = new Object[0]; // Массив для хранения объектов разных типов
    private JTextArea outputArea;
    private JComboBox<String> sortFieldComboBox;
    private JComboBox<String> dataSourceComboBox;
    private JComboBox<String> dataTypeComboBox; // Выпадающий список для выбора типа данных
    private JComboBox<String> sortParameterComboBox; // Выпадающий список для выбора параметра сортировки

    public DataManagementApp() {
        data = new Object[0];
        setTitle("Data Management Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5, 2)); // Увеличили количество строк для нового элемента

        JButton sortButton = new JButton("Sort");
        JButton searchButton = new JButton("Search");
        JButton inputButton = new JButton("Input Data");
        JButton exitButton = new JButton("Exit");

        String[] dataSources = {"File", "Manual Input", "Random"};
        dataSourceComboBox = new JComboBox<>(dataSources);

        String[] dataTypes = {"Bus", "User", "Student"}; // Список типов данных
        dataTypeComboBox = new JComboBox<>(dataTypes);

        // Выпадающий список для параметров сортировки (изначально пустой)
        sortParameterComboBox = new JComboBox<>();

        // Обновляем параметры сортировки при выборе типа данных
        dataTypeComboBox.addActionListener(e -> updateSortParameters());

        controlPanel.add(sortButton);
        controlPanel.add(sortParameterComboBox);
        controlPanel.add(searchButton);
        controlPanel.add(dataSourceComboBox);
        controlPanel.add(inputButton);
        controlPanel.add(dataTypeComboBox);
        controlPanel.add(exitButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Вызов нужных методов
        sortButton.addActionListener(e -> sortData());
        searchButton.addActionListener(e -> searchData());
        inputButton.addActionListener(e -> inputData());
        exitButton.addActionListener(e -> System.exit(0));
    }

    // Обновление параметров сортировки в зависимости от выбранного типа данных
    private void updateSortParameters() {
        String dataType = (String) dataTypeComboBox.getSelectedItem();
        String[] parameters;

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

    // Вспомогательный метод для увеличения размера массива
    private Object[] resizeArray(Object[] array, int newSize) {
        return Arrays.copyOf(array, newSize);
    }

    // Вспомогательный метод для добавления элемента в массив
    private void addToArray(Object element) {
        data = resizeArray(data, data.length + 1);
        data[data.length - 1] = element;
    }

    // Метод для сортировки данных
    private void sortData() {
        if (data.length == 0) {
            JOptionPane.showMessageDialog(this, "No data to sort.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String dataType = (String) dataTypeComboBox.getSelectedItem();
        String parameter = (String) sortParameterComboBox.getSelectedItem();

        switch (dataType) {
            case "Bus":
                Arrays.sort(data, (a, b) -> compareBuses((Bus) a, (Bus) b, parameter));
                break;
            case "User":
                Arrays.sort(data, (a, b) -> compareUsers((User) a, (User) b, parameter));
                break;
            case "Student":
                Arrays.sort(data, (a, b) -> compareStudents((Student) a, (Student) b, parameter));
                break;
        }

        displayData();
    }

    // Сравнение автобусов по выбранному параметру
    private int compareBuses(Bus a, Bus b, String parameter) {
        switch (parameter) {
            case "Number":
                return a.getNumber().compareTo(b.getNumber());
            case "Model":
                return a.getModel().compareTo(b.getModel());
            case "Mileage":
                return Integer.compare(a.getMileage(), b.getMileage());
            default:
                return 0;
        }
    }

    // Сравнение пользователей по выбранному параметру
    private int compareUsers(User a, User b, String parameter) {
        switch (parameter) {
            case "Name":
                return a.getName().compareTo(b.getName());
            case "Password":
                return a.getPassword().compareTo(b.getPassword());
            case "Email":
                return a.getEmail().compareTo(b.getEmail());
            default:
                return 0;
        }
    }

    // Сравнение студентов по выбранному параметру
    private int compareStudents(Student a, Student b, String parameter) {
        switch (parameter) {
            case "Group Number":
                return a.getGroupNumber().compareTo(b.getGroupNumber());
            case "Average Grade":
                return Double.compare(a.getAverageGrade(), b.getAverageGrade());
            case "Record Book Number":
                return a.getRecordBookNumber().compareTo(b.getRecordBookNumber());
            default:
                return 0;
        }
    }

    // Метод для поиска данных
    private void searchData() {
        String searchTerm = JOptionPane.showInputDialog(this, "Enter search term:");
        if (searchTerm != null && !searchTerm.isEmpty()) {
            Object[] results = Arrays.stream(data)
                    .filter(s -> s.toString().toLowerCase().contains(searchTerm.toLowerCase()))
                    .toArray();
            displaySearchResults(results);
        }
    }

    // Метод для ввода данных
    private void inputData() {
        String source = (String) dataSourceComboBox.getSelectedItem();
        String dataType = (String) dataTypeComboBox.getSelectedItem();

        switch (source) {
            case "File":
                loadFromFile();
                break;
            case "Manual Input":
                manualInput(dataType);
                break;
            case "Random":
                generateRandomData(dataType);
                break;
        }
    }

    // Метод для загрузки данных из файла
    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                data = new Object[0]; // Очистка массива
                String line;
                while ((line = reader.readLine()) != null) {
                    addToArray(line);
                }
                displayData();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Метод для ручного ввода данных
    private void manualInput(String dataType) {
        switch (dataType) {
            case "Bus":
                String busNumber = JOptionPane.showInputDialog(this, "Enter Bus Number:");
                String busModel = JOptionPane.showInputDialog(this, "Enter Bus Model:");
                int busMileage = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter Bus Mileage:"));
                addToArray(new Bus(busNumber, busModel, busMileage));
                break;
            case "User":
                String userName = JOptionPane.showInputDialog(this, "Enter User Name:");
                String userPassword = JOptionPane.showInputDialog(this, "Enter User Password:");
                String userEmail = JOptionPane.showInputDialog(this, "Enter User Email:");
                addToArray(new User(userName, userPassword, userEmail));
                break;
            case "Student":
                String groupNumber = JOptionPane.showInputDialog(this, "Enter Group Number:");
                double averageGrade = Double.parseDouble(JOptionPane.showInputDialog(this, "Enter Average Grade:"));
                String recordBookNumber = JOptionPane.showInputDialog(this, "Enter Record Book Number:");
                addToArray(new Student(groupNumber, averageGrade, recordBookNumber));
                break;
        }
        displayData();
    }

    // Метод для генерации случайных данных
    private void generateRandomData(String dataType) {
        Random random = new Random();
        switch (dataType) {
            case "Bus":
                for (int i = 0; i < 10; i++) {
                    String number = "Bus" + random.nextInt(100);
                    String model = "Model" + random.nextInt(10);
                    int mileage = random.nextInt(100000);
                    addToArray(new Bus(number, model, mileage));
                }
                break;
            case "User":
                for (int i = 0; i < 10; i++) {
                    String name = "User" + random.nextInt(100);
                    String password = "Pass" + random.nextInt(1000);
                    String email = "user" + random.nextInt(100) + "@example.com";
                    addToArray(new User(name, password, email));
                }
                break;
            case "Student":
                for (int i = 0; i < 10; i++) {
                    String groupNumber = "Group" + random.nextInt(10);
                    double averageGrade = 2 + random.nextDouble() * 3; // Оценка от 2 до 5
                    String recordBookNumber = "RB" + random.nextInt(1000);
                    addToArray(new Student(groupNumber, averageGrade, recordBookNumber));
                }
                break;
        }
        displayData();
    }

    // Метод для отображения данных
    private void displayData() {
        outputArea.setText("");
        for (Object item : data) {
            outputArea.append(item.toString() + "\n");
        }
    }

    // Метод для отображения результатов поиска
    private void displaySearchResults(Object[] results) {
        outputArea.setText("");
        for (Object item : results) {
            outputArea.append(item.toString() + "\n");
        }
    }

    // Точка входа в программу
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataManagementApp app = new DataManagementApp();
            app.setVisible(true);
        });
    }
}

// Класс Автобус
class Bus {
    private String number;
    private String model;
    private int mileage;

    public Bus(String number, String model, int mileage) {
        this.number = number;
        this.model = model;
        this.mileage = mileage;
    }

    public String getNumber() {
        return number;
    }

    public String getModel() {
        return model;
    }

    public int getMileage() {
        return mileage;
    }

    @Override
    public String toString() {
        return "Bus: " + number + ", " + model + ", " + mileage + " km";
    }
}

// Класс Пользователь
class User {
    private String name;
    private String password;
    private String email;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User: " + name + ", " + email;
    }
}

// Класс Студент
class Student {
    private String groupNumber;
    private double averageGrade;
    private String recordBookNumber;

    public Student(String groupNumber, double averageGrade, String recordBookNumber) {
        this.groupNumber = groupNumber;
        this.averageGrade = averageGrade;
        this.recordBookNumber = recordBookNumber;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public String getRecordBookNumber() {
        return recordBookNumber;
    }

    @Override
    public String toString() {
        return "Student: " + groupNumber + ", " + averageGrade + ", " + recordBookNumber;
    }
}
