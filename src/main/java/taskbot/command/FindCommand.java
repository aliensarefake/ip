package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.Ui;
import taskbot.task.Task;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class FindCommand extends Command {
    private final String keyword;
    
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchingTasks = tasks.find(keyword);
        ui.showLine();
        if (matchingTasks.isEmpty()) {
            System.out.println(" No matching tasks found.");
        } else {
            System.out.println(" Here are the matching tasks in your list:");
            IntStream.range(0, matchingTasks.size())
                .forEach(i -> System.out.println(" " + (i + 1) + "." + matchingTasks.get(i)));
        }
    }
    
    @Override
    public String executeAndGetResponse(TaskList tasks, Storage storage) {
        ArrayList<Task> matchingTasks = tasks.find(keyword);
        if (matchingTasks.isEmpty()) {
            return "No matching tasks found.";
        } else {
            String header = "Here are the matching tasks in your list:\n";
            String taskList = IntStream.range(0, matchingTasks.size())
                .mapToObj(i -> (i + 1) + "." + matchingTasks.get(i))
                .collect(java.util.stream.Collectors.joining("\n"));
            return header + taskList;
        }
    }
}
