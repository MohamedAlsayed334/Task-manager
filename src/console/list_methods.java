package console;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class list_methods {

    list_methods() {
        loadTasks();
    }

    public static List<List<String>> taskList = new ArrayList<>();
    private Random random = new Random();
    // private File file = new File("tasks.txt");

    // date formate 0000-00-00 00:00:00
    public String calculateDetailedRemainingTime(String creationDate, int daysToAdd) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime creation = LocalDateTime.parse(creationDate);
        LocalDateTime deadline = creation.plusDays(daysToAdd);

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

    public void add_task() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter task name: ");
        String task_name = scanner.nextLine().toLowerCase().strip();
        boolean valid_task_name = false;
        while (!valid_task_name) {
            if (!task_name.isEmpty() && task_name.length() < 20) {
                boolean task_exists = false;
                for (List<String> task : taskList) {
                    if (task_name.equals(task.get(1))) {
                        System.out.println("Task already exists.");
                        System.out.print("Enter task name: ");
                        task_name = scanner.nextLine().toLowerCase().strip();
                        task_exists = true;
                        break;
                    }
                }
                if (!task_exists) {
                    valid_task_name = true;
                }
            } else {
                System.out.print("Invalid task name. Enter task name: ");
                task_name = scanner.nextLine().toLowerCase().strip();
            }
        }

        int task_id = make_unique_id();

        System.out.print("Enter task deadline (days from now): ");
        int deadline = 0;
        LocalDateTime currentTime = LocalDateTime.now();
        boolean valid_deadline = false;
        while (!valid_deadline) {
            try {
                deadline = Integer.parseInt(scanner.nextLine().strip());
            } catch (NumberFormatException e) {
                System.out.print("Invalid deadline. Enter task deadline: ");
                continue;
            }
            if (deadline >= 0) {
                valid_deadline = true;
            }
        }

        String task_state = "Pending..";
        System.out.print("Enter task priority(high,medium,low): ");
        String priority = scanner.nextLine().toLowerCase().strip();
        String[] Priority_options = { "high", "medium", "low" };
        boolean valid_priority = false;
        while (!valid_priority) {
            for (String option : Priority_options) {
                if (priority.equals(option)) {
                    valid_priority = true;
                    break;
                }
            }
            if (!valid_priority) {
                System.out.print("Invalid priority. Enter task priority(high,medium,low): ");
                priority = scanner.nextLine().toLowerCase().strip();
            }
        }

        List<String> task = new ArrayList<>();
        task.add(String.valueOf(task_id)); // index 0
        task.add(task_name); // index 1
        task.add(String.valueOf(deadline)); // index 2 - deadline in days
        task.add(String.valueOf(currentTime)); // index 3 - creation time
        task.add(task_state); // index 4 - state
        task.add(priority); // index 5 - priority

        taskList.add(task);
        saveTasks();
        System.out.println("Task added successfully!");
    }

    public void saveTasks() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"));
            for (List<String> task : taskList) {
                writer.write(task.get(0) + "," + task.get(1) + "," + task.get(2) + "," + task.get(3) + ","
                        + task.get(4) + "," + task.get(5) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    public void loadTasks() {
        try {
            BufferedReader read = new BufferedReader(new FileReader("tasks.txt"));
            String line;
            while ((line = read.readLine()) != null) {
                List<String> temp = new ArrayList<>();
                String[] data = line.split(",");
                temp.add(data[0]);
                temp.add(data[1]);
                temp.add(data[2]);
                temp.add(data[3]);
                temp.add(data[4]);
                temp.add(data[5]);
                taskList.add(temp);
            }
            read.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks.");
        }
    }

    public static String center(String message, int len, char pad) {
        if (len < message.length())
            return message.substring(0, len - 2) + "..";
        int left = (len - message.length()) / 2;
        int right = (len - message.length() - left);
        String str = String.valueOf(pad);
        return str.repeat(left) + message + str.repeat(right);

    }

    public void finish_task() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to finish.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Finish by name or id ?\n1- Name\n2- ID\nChoice: ");
        int choice = scanner.nextInt();
        scanner.skip("\\R?");
        if (choice == 1) {
            System.out.print("Enter task name: ");
            String taskName = scanner.nextLine().strip();
            boolean task_exists = false;
            for (List<String> task : taskList) {
                if (taskName.equals(task.get(1))) {
                    task.set(4, "true");
                    saveTasks();
                    System.out.println("Task Finished successfully!");
                    task_exists = true;
                    break;
                }
            }
            if (!task_exists) {
                System.out.println("Task not found.");
            }
        } else if (choice == 2) {
            System.out.println("Enter task id: ");
            String taskName = scanner.nextLine().strip();
            boolean task_exists = false;
            for (List<String> task : taskList) {
                if (taskName.equals(task.get(0))) {
                    task.set(4, "true");
                    saveTasks();
                    System.out.println("Task Finished successfully!");
                    task_exists = true;
                    break;
                }
            }
            if (!task_exists) {
                System.out.println("Task not found.");
            }
        } else {
            System.out.println("No tasks to finish.");
        }

    }

    public void remove_task() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to remove.");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Remove by name or id ?\n1- Name\n2- ID\nChoice: ");
        int choice = scanner.nextInt();
        scanner.skip("\\R?"); // used as nextInt() let \n after it
        // \\R = Matches any Unicode linebreak (\n, \r\n, \r, etc.)
        if (choice == 1) {
            System.out.print("Enter task name: ");
            String taskName = scanner.nextLine().strip();
            boolean task_exists = false;
            for (List<String> task : taskList) {
                if (taskName.equals(task.get(1))) {
                    taskList.remove(task);
                    saveTasks();
                    System.out.println("Task removed successfully!");
                    task_exists = true;
                    break;
                }
            }
            if (!task_exists) {
                System.out.println("Task not found.");
            }
        } else if (choice == 2) {

            System.out.print("Enter task name: ");
            String taskName = scanner.nextLine().strip();
            boolean task_exists = false;
            for (List<String> task : taskList) {
                if (taskName.equals(task.get(0))) {
                    taskList.remove(task);
                    saveTasks();
                    System.out.println("Task removed successfully!");
                    task_exists = true;
                    break;
                }
            }
            if (!task_exists) {
                System.out.println("Task not found.");
            }
        }

    }

    public void viewTasks() {
        if (taskList.isEmpty()) {
            System.out.println("No tasks to display.");
        } else {
            Byte col1_width = 20;
            Byte col2_width = 15;
            Byte col3_width = 15;
            Byte col4_width = 15;
            Byte col5_width = 15;
            int list_len = taskList.size();
            String top_border = "┌────────────────────┬───────────────┬───────────────┬───────────────┬──────────────────┐";
            String mid_border = "├────────────────────┼───────────────┼───────────────┼───────────────┼──────────────────┤";
            String bottom_border = "└────────────────────┴───────────────┴───────────────┴───────────────┴──────────────────┘";

            System.out.println(top_border);
            System.out.println(
                    "|       Task         |      ID       |   Priority    |     State     |   Time Left      |");
            System.out.println(mid_border);

            for (List<String> task : taskList) {
                String timeLeft = calculateDetailedRemainingTime(task.get(3), Integer.parseInt(task.get(2)));
                String displayState = task.get(4);
                String displayTimeLeft = timeLeft;

                if (timeLeft.equals("EXPIRED!")) {
                    displayState = "Expired";
                    displayTimeLeft = "EXPIRED";
                } else if (task.get(4).equals("true") || task.get(4).equals("Finished")) {
                    displayState = "Finished";
                    displayTimeLeft = "FINISHED";
                }

                System.out.printf("|%s|%s|%s|%s|%s|\n",
                        center(task.get(1), col1_width, ' '),
                        center(task.get(0), col2_width, ' '),
                        center(task.get(5), col3_width, ' '),
                        center(displayState, col4_width, ' '),
                        center(displayTimeLeft, col5_width + 3, ' '));
                if (list_len > 1) {
                    System.out.println(mid_border);
                    list_len--;
                }
            }
            System.out.println(bottom_border);
        }
    }
}
