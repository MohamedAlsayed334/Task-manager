package UI;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.time.*;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class ToDoApp
        extends JPanel {
    private List<List<String>> taskList = new ArrayList<>();
    private Random random = new Random();
    private JTextField task_name_input;
    private JTextField task_time_input;
    private JComboBox task_priority_input;
    private String[] columnNames = { "Task ID", "Task Name", "Priority", "Status" };
    private DefaultTableModel tableModel;
    private JTable table;
    Color bg1 = new Color(44, 62, 80);
    Color bg2 = new Color(52, 73, 94);

    ToDoApp() {
        start_ui();
        loadTasks();

    }

    public void start_ui() {
        int width = 1000;
        int height = 700;
        JFrame frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        // top part
        JPanel top = new JPanel(new GridLayout(3, 2, 2, 3));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Add New Task");
        titledBorder.setTitleFont(new Font("Arial", Font.BOLD, 20));
        top.setBorder(titledBorder);

        JLabel task_name = new JLabel("  Add Task name:");
        task_name.setFont(new Font("Arial", Font.BOLD, 18));
        task_name_input = new JTextField();
        task_name_input.setFont(new Font("Arial", Font.BOLD, 16));
        top.add(task_name);
        top.add(task_name_input);

        JLabel task_time = new JLabel("  Add Task Days:");
        task_time.setFont(new Font("Arial", Font.BOLD, 18));
        task_time_input = new JTextField();
        task_time_input.setFont(new Font("Arial", Font.BOLD, 16));
        top.add(task_time);
        top.add(task_time_input);

        JLabel Priority = new JLabel("  Add Task Priority:");
        Priority.setFont(new Font("Arial", Font.BOLD, 18));
        task_priority_input = new JComboBox<>(new String[] { "High", "Medium", "Low" });
        task_priority_input.setFont(new Font("Arial", Font.BOLD, 16));
        top.add(Priority);
        top.add(task_priority_input);

        // mid
        JPanel mid = new JPanel();
        mid.setLayout(new BorderLayout());
        mid.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tableModel = new DefaultTableModel(columnNames, 0);

        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        table.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        mid.add(scrollPane);
        // bottom part
        JPanel bottom = new JPanel();
        bottom.setLayout(new GridLayout(1, 4, 5, 5));
        bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        TitledBorder footer = BorderFactory.createTitledBorder("Methods");
        footer.setTitleFont(new Font("Arial", Font.BOLD, 20));
        bottom.setBorder(footer);
        JButton add_task = new JButton("Add Task");
        add_task.setFont(new Font("Arial", Font.BOLD, 16));
        add_task.addActionListener(e -> addTask());
        bottom.add(add_task);
        JButton finish_task = new JButton("Finish Task");
        finish_task.setFont(new Font("Arial", Font.BOLD, 16));

        finish_task.addActionListener(e -> finishTask());
        bottom.add(finish_task);
        JButton remove_task = new JButton("Remove Task");
        remove_task.setFont(new Font("Arial", Font.BOLD, 16));

        remove_task.addActionListener(e -> removeTask());
        bottom.add(remove_task);
        JButton remove_all_tasks = new JButton("Remove All Tasks");
        remove_all_tasks.setFont(new Font("Arial", Font.BOLD, 16));

        remove_all_tasks.addActionListener(e -> removeAllTasks());

        bottom.add(remove_all_tasks);

        JButton refresh = new JButton("Refresh");
        refresh.setFont(new Font("Arial", Font.BOLD, 16));

        refresh.addActionListener(e -> refreshDisplay());
        bottom.add(refresh);

        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2));
        // Style buttons
        add_task.setBackground(new Color(46, 204, 113));
        add_task.setFocusPainted(false);

        finish_task.setBackground(new Color(52, 152, 219));
        finish_task.setFocusPainted(false);

        remove_task.setBackground(new Color(231, 76, 60));
        remove_task.setFocusPainted(false);

        remove_all_tasks.setBackground(new Color(155, 89, 182));
        remove_all_tasks.setFocusPainted(false);

        refresh.setBackground(new Color(241, 196, 15));
        refresh.setFocusPainted(false);
        frame.add(top, BorderLayout.NORTH);
        frame.add(mid, BorderLayout.CENTER);
        frame.add(bottom, BorderLayout.SOUTH);
        frame.setVisible(true);

    }

    public int make_unique_id() {
        int id = random.nextInt(100000);
        boolean unique = false;
        while (!unique) {
            unique = true;
            for (List<String> task : taskList) {
                if (Integer.parseInt(task.get(0)) == id) {
                    id = random.nextInt(100000);
                    unique = false;
                    break;
                }
            }
        }
        return id;
    }

    public String calculateDetailedRemainingTime(LocalDateTime creationDate, int daysToAdd) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = creationDate.plusDays(daysToAdd);

        Duration duration = Duration.between(now, deadline);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        if (duration.isNegative()) {
            return "EXPIRED!";
        } else if (days == 0) {
            return String.format("%d hours, %d minutes", hours, minutes);
        } else {
            return String.format("%d days, %d hours, %d minutes", days, hours, minutes);
        }
    }

    public void addTask() {
        String taskName = task_name_input.getText().strip();
        String taskPriority = (String) task_priority_input.getSelectedItem();
        String task_id = String.valueOf(make_unique_id());
        int taskDays = 0;
        LocalDateTime creation_date = LocalDateTime.now();

        if (taskName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter a task name.");
            return;
        }

        if (taskName.length() >= 20) {
            JOptionPane.showMessageDialog(null, "Task name is very long.");
            return;
        }
        for (List<String> task : taskList) {
            if (task.get(1).equals(taskName)) {
                JOptionPane.showMessageDialog(null, "Task already exists.");
                return;
            }
        }
        try {
            taskDays = Integer.parseInt(task_time_input.getText().strip());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number of days.");
            return;

        }
        List<String> temp = new ArrayList<>();
        temp.add(task_id); // 0
        temp.add(taskName); // 1
        temp.add(taskPriority); // 2
        temp.add(String.valueOf(taskDays)); // 3
        temp.add(String.valueOf(creation_date)); // 4
        taskList.add(temp);
        tableModel.addRow(new String[] { task_id, taskName, taskPriority,
                calculateDetailedRemainingTime(creation_date, taskDays) });
        saveTasks();
    }

    public void finishTask() {
        if (taskList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tasks to finish!");
            return;
        }
        String input = JOptionPane.showInputDialog(this, "Enter task name or ID to finish:");
        if (input == null || input.trim().isEmpty())
            return;
        input = input.trim();
        for (List<String> task : taskList) {
            if (task.get(1).equals(input) || task.get(0).equals(input)) {
                task.set(4, "Finished");
                saveTasks();
                JOptionPane.showMessageDialog(this, "Task finished!");
                refreshDisplay();
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Task not found!");
    }

    public void removeTask() {
        if (taskList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tasks to Remove!");
            return;
        }
        String input = JOptionPane.showInputDialog(this, "Enter task name or ID to Remove:");
        if (input == null || input.strip().isEmpty())
            return;
        input = input.strip();
        for (List<String> task : taskList) {
            if (task.get(1).equals(input) || task.get(0).equals(input)) {
                taskList.remove(task);
                saveTasks();
                JOptionPane.showMessageDialog(this, "Task Removed!");
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Task not found!");
    }

    public void removeAllTasks() {
        if (taskList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No tasks to Remove!");
            return;
        }
        int input = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove all tasks?");
        if (input == JOptionPane.NO_OPTION || input == JOptionPane.CLOSED_OPTION)
            return;
        else if (input == JOptionPane.YES_OPTION) {
            taskList.clear();
            saveTasks();
            JOptionPane.showMessageDialog(this, "All tasks removed!");
            refreshDisplay();
        }
    }

    public void saveTasks() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"));
            for (List<String> task : taskList) {
                writer.write(String.join(",", task) + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void loadTasks() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                List temp = new ArrayList<>();
                temp.add(data[0]);
                temp.add(data[1]);
                temp.add(data[2]);
                temp.add(data[3]);
                temp.add(data[4]);
                taskList.add(temp);

            }
            refreshDisplay();
            reader.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void refreshDisplay() {
        for (int i = tableModel.getRowCount() - 1; i >= 0; i--) {
            tableModel.removeRow(i);
        }
        for (List<String> task : taskList) {
            tableModel.addRow(new String[] { task.get(0), task.get(1), task.get(2),
                    calculateDetailedRemainingTime(LocalDateTime.parse(task.get(4)), Integer.parseInt(task.get(3))) });
        }

    }

}