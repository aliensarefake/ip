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
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
        
        scanner.close();
    }
}
