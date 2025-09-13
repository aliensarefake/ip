package taskbot;

import taskbot.command.AddCommand;
import taskbot.command.Command;
import taskbot.command.DeleteCommand;
import taskbot.command.ExitCommand;
import taskbot.command.FindCommand;
import taskbot.command.ListCommand;
import taskbot.command.MarkCommand;
import taskbot.command.SortCommand;
import taskbot.command.UnmarkCommand;
import taskbot.task.Deadline;
import taskbot.task.Event;
import taskbot.task.ToDo;

/**
 * Parses user input and converts it into executable commands.
 * Handles various command types including todo, deadline, event, list, mark, unmark, delete, find, and bye.
 */
public final class Parser {
    private static final String ERROR_EMPTY_TASK_NUMBER = "OOPS!!! Please provide a task number to ";
    private static final String ERROR_INVALID_NUMBER = "OOPS!!! Please provide a valid task number.";
    private static final String ERROR_EMPTY_DESCRIPTION = "OOPS!!! The description of a ";
    private static final String ERROR_UNKNOWN_COMMAND = "OOPS!!! I'm sorry, but I don't know what that means :-(";
    
    private Parser() {
    }
    
    /**
     * Parses a full command string and returns the corresponding Command object.
     * 
     * @param fullCommand the complete command string entered by the user
     * @return the Command object representing the parsed command
     * @throws TaskBotException if the command is invalid or malformed
     */
    public static Command parse(String fullCommand) throws TaskBotException {
        assert fullCommand != null && !fullCommand.trim().isEmpty() : "Command cannot be null or empty";
        String[] parts = fullCommand.split(" ", 2);
        String commandWord = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";
        
        switch (commandWord) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(parseTaskNumber(arguments, "mark"));
            case "unmark":
                return new UnmarkCommand(parseTaskNumber(arguments, "unmark"));
            case "delete":
                return new DeleteCommand(parseTaskNumber(arguments, "delete"));
            case "todo":
                validateDescription(arguments, "todo");
                return new AddCommand(new ToDo(arguments.trim()));
            case "deadline":
                return parseDeadlineCommand(arguments);
            case "event":
                return parseEventCommand(arguments);
            case "find":
                if (arguments.trim().isEmpty()) {
                    throw new TaskBotException("OOPS!!! Please provide a keyword to search.");
                }
                return new FindCommand(arguments.trim());
            case "sort":
                return parseSortCommand(arguments);
            default:
                throw new TaskBotException(ERROR_UNKNOWN_COMMAND);
        }
    }
    
    private static int parseTaskNumber(String arguments, String action) throws TaskBotException {
        if (arguments.isEmpty()) {
            throw new TaskBotException(ERROR_EMPTY_TASK_NUMBER + action + ".");
        }
        try {
            int taskNum = Integer.parseInt(arguments);
            assert taskNum > 0 : "Task number must be positive";
            return taskNum;
        } catch (NumberFormatException e) {
            throw new TaskBotException(ERROR_INVALID_NUMBER);
        }
    }
    
    private static void validateDescription(String arguments, String taskType) throws TaskBotException {
        if (arguments.trim().isEmpty()) {
            throw new TaskBotException(ERROR_EMPTY_DESCRIPTION + taskType + " cannot be empty.");
        }
    }
    
    private static Command parseDeadlineCommand(String arguments) throws TaskBotException {
        validateDescription(arguments, "deadline");
        
        final String byDelimiter = " /by ";
        if (!arguments.contains(byDelimiter)) {
            throw new TaskBotException("OOPS!!! Please specify the deadline using /by format.");
        }
        
        String[] parts = arguments.split(byDelimiter);
        if (parts.length != 2) {
            throw new TaskBotException("OOPS!!! Please specify the deadline using /by format.");
        }
        
        return new AddCommand(new Deadline(parts[0], parts[1]));
    }
    
    private static Command parseEventCommand(String arguments) throws TaskBotException {
        validateDescription(arguments, "event");
        
        final String fromDelimiter = " /from ";
        final String toDelimiter = " /to ";
        final String errorMessage = "OOPS!!! Please specify the event time using /from and /to format.";
        
        if (!arguments.contains(fromDelimiter) || !arguments.contains(toDelimiter)) {
            throw new TaskBotException(errorMessage);
        }
        
        String[] eventParts = arguments.split(fromDelimiter);
        if (eventParts.length != 2) {
            throw new TaskBotException(errorMessage);
        }
        
        String[] timeParts = eventParts[1].split(toDelimiter);
        if (timeParts.length != 2) {
            throw new TaskBotException(errorMessage);
        }
        
        return new AddCommand(new Event(eventParts[0], timeParts[0], timeParts[1]));
    }
    
    private static Command parseSortCommand(String arguments) throws TaskBotException {
        String criteria = arguments.trim().toLowerCase();
        
        if (criteria.isEmpty()) {
            criteria = "description";
        }
        
        SortCommand.SortCriteria sortCriteria;
        switch (criteria) {
            case "description":
            case "desc":
                sortCriteria = SortCommand.SortCriteria.DESCRIPTION;
                break;
            case "status":
            case "done":
                sortCriteria = SortCommand.SortCriteria.STATUS;
                break;
            case "type":
                sortCriteria = SortCommand.SortCriteria.TYPE;
                break;
            case "date":
            case "time":
                sortCriteria = SortCommand.SortCriteria.DATE;
                break;
            default:
                throw new TaskBotException("OOPS!!! Invalid sort criteria. Use: description, status, type, or date.");
        }
        
        return new SortCommand(sortCriteria);
    }
}
