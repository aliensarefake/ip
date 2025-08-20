import java.util.Scanner;

public class Duke {
    public static void main(String[] args) {
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm TaskBot");
        System.out.println(" What can I do for you?");
        System.out.println("____________________________________________________________");
        
        Scanner scanner = new Scanner(System.in);
        String[] tasks = new String[100];
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
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("____________________________________________________________");
                System.out.println(" added: " + input);
                System.out.println("____________________________________________________________");
            }
        }
        
        scanner.close();
    }
}
