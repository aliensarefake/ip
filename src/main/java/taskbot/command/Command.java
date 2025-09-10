package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.TaskBotException;
import taskbot.Ui;

/**
 * Represents an abstract command that can be executed.
 */
public abstract class Command {
    protected static final String ERROR_OUT_OF_RANGE = "OOPS!!! Task number is out of range.";
    
    /**
     * Executes the command.
     * @param tasks The task list to operate on
     * @param ui The UI for user interaction
     * @param storage The storage for persistence
     * @throws TaskBotException If command execution fails
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws TaskBotException;
    
    /**
     * Executes the command and returns the response as a string.
     * @param tasks The task list to operate on
     * @param storage The storage for persistence
     * @return The response string from executing the command
     * @throws TaskBotException If command execution fails
     */
    public abstract String executeAndGetResponse(TaskList tasks, Storage storage) throws TaskBotException;
    
    /**
     * Checks if this command causes the application to exit.
     * @return true if this is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }
    
    /**
     * Validates that a task number is within valid range.
     * @param taskNum The task number to validate (1-indexed)
     * @param listSize The size of the task list
     * @throws TaskBotException If the task number is out of range
     */
    protected void validateTaskNumber(int taskNum, int listSize) throws TaskBotException {
        if (taskNum < 1 || taskNum > listSize) {
            throw new TaskBotException(ERROR_OUT_OF_RANGE);
        }
    }
}
