package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.TaskBotException;
import taskbot.Ui;
import taskbot.task.Task;

public class UnmarkCommand extends Command {
    private int taskNum;
    
    public UnmarkCommand(int taskNum) {
        this.taskNum = taskNum;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TaskBotException {
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new TaskBotException("OOPS!!! Task number is out of range.");
        }
        Task task = tasks.get(taskNum - 1);
        task.markAsNotDone();
        storage.save(tasks.getTasks());
        ui.showMarkedNotDone(task);
    }
    
    @Override
    public String executeAndGetResponse(TaskList tasks, Storage storage) throws TaskBotException {
        if (taskNum < 1 || taskNum > tasks.size()) {
            throw new TaskBotException("OOPS!!! Task number is out of range.");
        }
        Task task = tasks.get(taskNum - 1);
        task.markAsNotDone();
        storage.save(tasks.getTasks());
        return "OK, I've marked this task as not done yet:\n  " + task;
    }
}
