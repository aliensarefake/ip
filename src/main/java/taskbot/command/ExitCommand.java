package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.Ui;

public class ExitCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showGoodbye();
    }
    
    @Override
    public String executeAndGetResponse(TaskList tasks, Storage storage) {
        return "Bye. Hope to see you again soon!";
    }
    
    @Override
    public boolean isExit() {
        return true;
    }
}
