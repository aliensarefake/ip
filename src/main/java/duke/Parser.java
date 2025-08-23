package duke;

import duke.command.*;
import duke.task.*;

public class Parser {
    
    public static Command parse(String fullCommand) throws TaskBotException {
        String[] parts = fullCommand.split(" ", 2);
        String commandWord = parts[0];
        String arguments = parts.length > 1 ? parts[1] : "";
        
        switch (commandWord) {
            case "bye":
                return new ExitCommand();
            case "list":
                return new ListCommand();
            case "mark":
                if (arguments.isEmpty()) {
                    throw new TaskBotException("OOPS!!! Please provide a task number to mark.");
                }
                try {
                    int taskNum = Integer.parseInt(arguments);
                    return new MarkCommand(taskNum);
                } catch (NumberFormatException e) {
                    throw new TaskBotException("OOPS!!! Please provide a valid task number.");
                }
            case "unmark":
                if (arguments.isEmpty()) {
                    throw new TaskBotException("OOPS!!! Please provide a task number to unmark.");
                }
                try {
                    int taskNum = Integer.parseInt(arguments);
                    return new UnmarkCommand(taskNum);
                } catch (NumberFormatException e) {
                    throw new TaskBotException("OOPS!!! Please provide a valid task number.");
                }
            case "delete":
                if (arguments.isEmpty()) {
                    throw new TaskBotException("OOPS!!! Please provide a task number to delete.");
                }
                try {
                    int taskNum = Integer.parseInt(arguments);
                    return new DeleteCommand(taskNum);
                } catch (NumberFormatException e) {
                    throw new TaskBotException("OOPS!!! Please provide a valid task number.");
                }
            case "todo":
                if (arguments.trim().isEmpty()) {
                    throw new TaskBotException("OOPS!!! The description of a todo cannot be empty.");
                }
                return new AddCommand(new ToDo(arguments.trim()));
            case "deadline":
                if (arguments.trim().isEmpty()) {
                    throw new TaskBotException("OOPS!!! The description of a deadline cannot be empty.");
                }
                if (!arguments.contains(" /by ")) {
                    throw new TaskBotException("OOPS!!! Please specify the deadline using /by format.");
                }
                String[] deadlineParts = arguments.split(" /by ");
                if (deadlineParts.length != 2) {
                    throw new TaskBotException("OOPS!!! Please specify the deadline using /by format.");
                }
                return new AddCommand(new Deadline(deadlineParts[0], deadlineParts[1]));
            case "event":
                if (arguments.trim().isEmpty()) {
                    throw new TaskBotException("OOPS!!! The description of an event cannot be empty.");
                }
                if (!arguments.contains(" /from ") || !arguments.contains(" /to ")) {
                    throw new TaskBotException("OOPS!!! Please specify the event time using /from and /to format.");
                }
                String[] eventParts = arguments.split(" /from ");
                if (eventParts.length != 2) {
                    throw new TaskBotException("OOPS!!! Please specify the event time using /from and /to format.");
                }
                String[] timeParts = eventParts[1].split(" /to ");
                if (timeParts.length != 2) {
                    throw new TaskBotException("OOPS!!! Please specify the event time using /from and /to format.");
                }
                return new AddCommand(new Event(eventParts[0], timeParts[0], timeParts[1]));
            case "find":
                if (arguments.trim().isEmpty()) {
                    throw new TaskBotException("OOPS!!! Please provide a keyword to search.");
                }
                return new FindCommand(arguments.trim());
            default:
                throw new TaskBotException("OOPS!!! I'm sorry, but I don't know what that means :-(");
        }
    }
}
