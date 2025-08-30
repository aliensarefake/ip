package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.TaskBotException;
import taskbot.Ui;
import taskbot.task.Task;

public class DeleteCommand extends Command {
    private int taskNum;
    
    public DeleteCommand(int taskNum) {
        this.taskNum = taskNum;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TaskBotException {
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new TaskBotException("OOPS!!! Task number is out of range.");
        }
        Task removedTask = tasks.get(taskNum - 1);
        tasks.delete(taskNum - 1);
        storage.save(tasks.getTasks());
        ui.showTaskRemoved(removedTask, tasks.size());
    }
    
    @Override
    public String executeAndGetResponse(TaskList tasks, Storage storage) throws TaskBotException {
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new TaskBotException("OOPS!!! Task number is out of range.");
        }
        Task removedTask = tasks.get(taskNum - 1);
        tasks.delete(taskNum - 1);
        storage.save(tasks.getTasks());
        return "Noted. I've removed this task:\n  " + removedTask + "\nNow you have " + tasks.size() + " tasks in the list.";
    }
}
