package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.TaskBotException;
import duke.Ui;
import duke.task.Task;

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
}
