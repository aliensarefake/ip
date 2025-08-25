package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.Ui;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
}
