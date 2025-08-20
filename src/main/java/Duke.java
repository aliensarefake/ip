import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm TaskBot");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
        
        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;
        String input;
        
        while (true) {
            input = scanner.nextLine();
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("mark ")) {
                int taskNum = Integer.parseInt(input.substring(5)) - 1;
                tasks[taskNum].markAsDone();
                System.out.println("____________________________________________________________");
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + tasks[taskNum]);
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("unmark ")) {
                int taskNum = Integer.parseInt(input.substring(7)) - 1;
                tasks[taskNum].markAsNotDone();
                System.out.println("____________________________________________________________");
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + tasks[taskNum]);
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("delete ")) {
                try {
                    int taskNum = Integer.parseInt(input.substring(7)) - 1;
                    if (taskNum < 0 || taskNum >= taskCount) {
                        throw new DukeException("OOPS!!! Task number is out of range.");
                    }
                    Task removedTask = tasks[taskNum];
                    for (int i = taskNum; i < taskCount - 1; i++) {
                        tasks[i] = tasks[i + 1];
                    }
                    taskCount--;
                    System.out.println("____________________________________________________________");
                    System.out.println(" Noted. I've removed this task:");
                    System.out.println("   " + removedTask);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } catch (NumberFormatException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" OOPS!!! Please provide a valid task number.");
                    System.out.println("____________________________________________________________");
                } catch (DukeException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" " + e.getMessage());
                    System.out.println("____________________________________________________________");
                }
            } else if (input.startsWith("todo ")) {
                try {
                    String description = input.substring(5).trim();
                    if (description.isEmpty()) {
                        throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    tasks[taskCount] = new ToDo(description);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[taskCount]);
                    taskCount++;
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } catch (DukeException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" " + e.getMessage());
                    System.out.println("____________________________________________________________");
                }
            } else if (input.equals("todo")) {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS!!! The description of a todo cannot be empty.");
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("deadline ")) {
                try {
                    String remaining = input.substring(9).trim();
                    if (remaining.isEmpty()) {
                        throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    if (!remaining.contains(" /by ")) {
                        throw new DukeException("OOPS!!! Please specify the deadline using /by format.");
                    }
                    String[] parts = remaining.split(" /by ");
                    String description = parts[0];
                    String by = parts[1];
                    tasks[taskCount] = new Deadline(description, by);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[taskCount]);
                    taskCount++;
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } catch (DukeException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" " + (e instanceof DukeException ? e.getMessage() : "OOPS!!! Please specify the deadline using /by format."));
                    System.out.println("____________________________________________________________");
                }
            } else if (input.equals("deadline")) {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS!!! The description of a deadline cannot be empty.");
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("event ")) {
                try {
                    String remaining = input.substring(6).trim();
                    if (remaining.isEmpty()) {
                        throw new DukeException("OOPS!!! The description of an event cannot be empty.");
                    }
                    if (!remaining.contains(" /from ") || !remaining.contains(" /to ")) {
                        throw new DukeException("OOPS!!! Please specify the event time using /from and /to format.");
                    }
                    String[] parts = remaining.split(" /from ");
                    String description = parts[0];
                    String[] timeParts = parts[1].split(" /to ");
                    String from = timeParts[0];
                    String to = timeParts[1];
                    tasks[taskCount] = new Event(description, from, to);
                    System.out.println("____________________________________________________________");
                    System.out.println(" Got it. I've added this task:");
                    System.out.println("   " + tasks[taskCount]);
                    taskCount++;
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                    System.out.println("____________________________________________________________");
                } catch (DukeException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("____________________________________________________________");
                    System.out.println(" " + (e instanceof DukeException ? e.getMessage() : "OOPS!!! Please specify the event time using /from and /to format."));
                    System.out.println("____________________________________________________________");
                }
            } else if (input.equals("event")) {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS!!! The description of an event cannot be empty.");
                System.out.println("____________________________________________________________");
            } else {
                System.out.println("____________________________________________________________");
                System.out.println(" OOPS!!! I'm sorry, but I don't know what that means :-(");
                System.out.println("____________________________________________________________");
            }
        }
        
        scanner.close();
    }
}
