package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.TaskBotException;
import taskbot.Ui;
import taskbot.task.Task;

public class MarkCommand extends Command {
    private final int taskNum;
    
    public MarkCommand(int taskNum) {
        this.taskNum = taskNum;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TaskBotException {
        validateTaskNumber(taskNum, tasks.size());
        Task task = tasks.get(taskNum - 1);
        task.markAsDone();
        storage.save(tasks.getTasks());
        ui.showMarkedDone(task);
    }
    
    @Override
    public String executeAndGetResponse(TaskList tasks, Storage storage) throws TaskBotException {
        validateTaskNumber(taskNum, tasks.size());
        Task task = tasks.get(taskNum - 1);
        task.markAsDone();
        storage.save(tasks.getTasks());
        return "Nice! I've marked this task as done:\n  " + task;
    }
}
