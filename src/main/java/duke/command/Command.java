package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.TaskBotException;
import duke.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws TaskBotException;
    
    public boolean isExit() {
        return false;
    }
}