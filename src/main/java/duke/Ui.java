package duke;

import duke.task.Task;
import java.util.Scanner;

public class Ui {
    private Scanner scanner;
    
    public Ui() {
        scanner = new Scanner(System.in);
    }
    
    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm TaskBot");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }
    
    public void showLine() {
        System.out.println("____________________________________________________________");
    }
    
    public void showLoadingError() {
        System.out.println("Error loading tasks from file.");
    }
    
    public void showError(String message) {
        System.out.println(" " + message);
    }
    
    public String readCommand() {
        return scanner.nextLine();
    }
    
    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }
    
    public void showTaskList(TaskList tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
    }
    
    public void showMarkedDone(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }
    
    public void showMarkedNotDone(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
    }
    
    public void showTaskRemoved(Task task, int taskCount) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }
    
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }
    
    public void close() {
        scanner.close();
    }
}
