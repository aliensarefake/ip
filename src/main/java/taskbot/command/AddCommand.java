package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.TaskBotException;
import taskbot.Ui;
import taskbot.task.Task;

public class AddCommand extends Command {
    private Task task;
    
    public AddCommand(Task task) {
        this.task = task;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TaskBotException {
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
    
    @Override
    public String executeAndGetResponse(TaskList tasks, Storage storage) throws TaskBotException {
        tasks.add(task);
        storage.save(tasks.getTasks());
        return "Got it. I've added this task:\n  " + task + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
