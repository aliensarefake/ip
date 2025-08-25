package taskbot;

import taskbot.task.Task;
import java.util.Scanner;

/**
 * Handles all user interface operations for the TaskBot application.
 * Manages input/output operations and message formatting.
 */
public class Ui {
    private Scanner scanner;
    
    /**
     * Constructs a new Ui instance with a scanner for reading user input.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }
    
    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm TaskBot");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Displays a horizontal line separator.
     */
    public void showLine() {
        System.out.println("____________________________________________________________");
    }
    
    /**
     * Displays an error message when tasks fail to load from file.
     */
    public void showLoadingError() {
        System.out.println("Error loading tasks from file.");
    }
    
    /**
     * Displays an error message to the user.
     * 
     * @param message the error message to display
     */
    public void showError(String message) {
        System.out.println(" " + message);
    }
    
    /**
     * Reads a command from the user.
     * 
     * @return the command entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }
    
    /**
     * Displays the goodbye message when the user exits.
     */
    public void showGoodbye() {
        System.out.println(" Bye. Hope to see you again soon!");
    }
    
    /**
     * Displays the list of all tasks.
     * 
     * @param tasks the TaskList containing all tasks
     */
    public void showTaskList(TaskList tasks) {
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + "." + tasks.get(i));
        }
    }
    
    /**
     * Displays a message when a task is marked as done.
     * 
     * @param task the task that was marked as done
     */
    public void showMarkedDone(Task task) {
        System.out.println(" Nice! I've marked this task as done:");
        System.out.println("   " + task);
    }
    
    /**
     * Displays a message when a task is marked as not done.
     * 
     * @param task the task that was marked as not done
     */
    public void showMarkedNotDone(Task task) {
        System.out.println(" OK, I've marked this task as not done yet:");
        System.out.println("   " + task);
    }
    
    /**
     * Displays a message when a task is removed.
     * 
     * @param task the task that was removed
     * @param taskCount the number of tasks remaining
     */
    public void showTaskRemoved(Task task, int taskCount) {
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }
    
    /**
     * Displays a message when a new task is added.
     * 
     * @param task the task that was added
     * @param taskCount the total number of tasks
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + taskCount + " tasks in the list.");
    }
    
    /**
     * Closes the scanner resource.
     */
    public void close() {
        scanner.close();
    }
}
