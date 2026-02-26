package console;

import java.util.Scanner;

public class App_Runner {

    static void Menu() {
        String[] menu = {
                "╔────────────────────────────────────────╗",
                "│                   Menu                 │",
                "│ 0  - Exit                              │",
                "│ 1  - Add Task                          │",
                "│ 2  - Finish Task                       │",
                "│ 3  - Remove Task                       │",
                "│ 4  - View Tasks                        │",
                "│────────────────────────────────────────│",
                "│           Task Manager App             │",
                "╚────────────────────────────────────────╝"
        };

        for (String line : menu) {
            System.out.println(line);
        }
    }

    public static void main(String[] args) {
        list_methods list = new list_methods();
        while (true) {
            Menu();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Exiting the application. Goodbye!");
                    return;
                case 1:
                    list.add_task();
                    break;
                case 2:
                    list.finish_task();
                    break;
                case 3:
                    list.remove_task();
                    break;
                case 4:
                    list.viewTasks();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

    }
}
