package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.TaskBotException;
import duke.Ui;
import duke.task.Task;

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
}
