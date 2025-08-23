package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.TaskBotException;
import duke.Ui;

/**
 * Represents an abstract command that can be executed.
 */
public abstract class Command {
    /**
     * Executes the command.
     * @param tasks The task list to operate on
     * @param ui The UI for user interaction
     * @param storage The storage for persistence
     * @throws TaskBotException If command execution fails
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws TaskBotException;
    
    /**
     * Checks if this command causes the application to exit.
     * @return true if this is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
}