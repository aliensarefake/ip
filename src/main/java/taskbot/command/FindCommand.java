package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.Ui;
import taskbot.task.Task;
import java.util.ArrayList;

public class FindCommand extends Command {
    private String keyword;
    
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
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println(" " + (i + 1) + "." + matchingTasks.get(i));
            }
        }
    }
    
    @Override
    public String executeAndGetResponse(TaskList tasks, Storage storage) {
        ArrayList<Task> matchingTasks = tasks.find(keyword);
        if (matchingTasks.isEmpty()) {
            return "No matching tasks found.";
        } else {
            StringBuilder result = new StringBuilder("Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                result.append((i + 1)).append(".").append(matchingTasks.get(i)).append("\n");
            }
            return result.toString().trim();
        }
    }
}
