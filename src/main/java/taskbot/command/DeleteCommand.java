package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.TaskBotException;
import taskbot.Ui;
import taskbot.task.Task;

public class DeleteCommand extends Command {
    private final int taskNum;
    
    public DeleteCommand(int taskNum) {
        assert taskNum > 0 : "Task number must be positive";
        this.taskNum = taskNum;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TaskBotException {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";
        validateTaskNumber(taskNum, tasks.size());
        Task removedTask = tasks.get(taskNum - 1);
        tasks.delete(taskNum - 1);
        storage.save(tasks.getTasks());
        ui.showTaskRemoved(removedTask, tasks.size());
    }
    
    @Override
    public String executeAndGetResponse(TaskList tasks, Storage storage) throws TaskBotException {
        assert tasks != null : "TaskList cannot be null";
        assert storage != null : "Storage cannot be null";
        validateTaskNumber(taskNum, tasks.size());
        Task removedTask = tasks.get(taskNum - 1);
        tasks.delete(taskNum - 1);
        storage.save(tasks.getTasks());
        return "Noted. I've removed this task:\n  " + removedTask
                + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
