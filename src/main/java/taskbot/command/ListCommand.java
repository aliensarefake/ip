package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
    
    @Override
    public String executeAndGetResponse(TaskList tasks, Storage storage) {
        if (tasks.size() == 0) {
            return "No tasks in your list yet.";
        }
        StringBuilder result = new StringBuilder("Here are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            result.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
        }
        return result.toString().trim();
    }
}
