package main.java.org.example.UI;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class DataManagementApp extends JFrame {
    private List<String> data;
    private JTextArea outputArea;
    private JComboBox<String> sortFieldComboBox;
    private JComboBox<String> dataSourceComboBox;

    public DataManagementApp() {
        data = new ArrayList<>();
        setTitle("Data Management Application");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 2));

        JButton sortButton = new JButton("Sort");
        JButton searchButton = new JButton("Search");
        JButton inputButton = new JButton("Input Data");
        JButton exitButton = new JButton("Exit");

        String[] sortFields = {"Name", "Age", "ID"};
        sortFieldComboBox = new JComboBox<>(sortFields);

        String[] dataSources = {"File", "Manual Input", "Random"};
        dataSourceComboBox = new JComboBox<>(dataSources);

        controlPanel.add(sortButton);
        controlPanel.add(sortFieldComboBox);
        controlPanel.add(searchButton);
        controlPanel.add(dataSourceComboBox);
        controlPanel.add(inputButton);
        controlPanel.add(exitButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        //Вызов нужных методов
        sortButton.addActionListener(e -> sortData());
        searchButton.addActionListener(e -> searchData());
        inputButton.addActionListener(e -> inputData());
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void sortData() {
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No data to sort.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String field = (String) sortFieldComboBox.getSelectedItem();
        Collections.sort(data, (a, b) -> a.split(",")[getFieldIndex(field)].compareTo(b.split(",")[getFieldIndex(field)]));
        displayData();
    }

    private int getFieldIndex(String field) {
        switch (field) {
            case "Name": return 0;
            case "Age": return 1;
            case "ID": return 2;
            default: return 0;
        }
    }

    private void searchData() {
        String searchTerm = JOptionPane.showInputDialog(this, "Enter search term:");
        if (searchTerm != null && !searchTerm.isEmpty()) {
            List<String> results = data.stream()
                    .filter(s -> s.toLowerCase().contains(searchTerm.toLowerCase()))
                    .toList();
            displaySearchResults(results);
        }
    }

    private void inputData() {
        String source = (String) dataSourceComboBox.getSelectedItem();
        switch (source) {
            case "File":
                loadFromFile();
                break;
            case "Manual Input":
                manualInput();
                break;
            case "Random":
                generateRandomData();
                break;
        }
    }

    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                data.clear();
                String line;
                while ((line = reader.readLine()) != null) {
                    data.add(line);
                }
                displayData();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error reading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void manualInput() {
        String input = JOptionPane.showInputDialog(this, "Enter data (Name,Age,ID):");
        if (input != null && !input.isEmpty()) {
            data.add(input);
            displayData();
        }
    }

    private void generateRandomData() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            String name = "Person" + i;
            int age = random.nextInt(50) + 18;
            int id = random.nextInt(1000) + 1000;
            data.add(name + "," + age + "," + id);
        }
        displayData();
    }

    private void displayData() {
        outputArea.setText("");
        for (String item : data) {
            outputArea.append(item + "\n");
        }
    }

    private void displaySearchResults(List<String> results) {
        outputArea.setText("");
        for (String item : results) {
            outputArea.append(item + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataManagementApp app = new DataManagementApp();
            app.setVisible(true);
        });
    }
}