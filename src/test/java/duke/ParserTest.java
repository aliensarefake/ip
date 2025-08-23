package duke;

import duke.command.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    
    @Test
    public void testParseExitCommand() throws TaskBotException {
        Command command = Parser.parse("bye");
        assertTrue(command instanceof ExitCommand);
        assertTrue(command.isExit());
    }
    
    @Test
    public void testParseListCommand() throws TaskBotException {
        Command command = Parser.parse("list");
        assertTrue(command instanceof ListCommand);
        assertFalse(command.isExit());
    }
    
    @Test
    public void testParseMarkCommand() throws TaskBotException {
        Command command = Parser.parse("mark 1");
        assertTrue(command instanceof MarkCommand);
    }
    
    @Test
    public void testParseUnmarkCommand() throws TaskBotException {
        Command command = Parser.parse("unmark 2");
        assertTrue(command instanceof UnmarkCommand);
    }
    
    @Test
    public void testParseDeleteCommand() throws TaskBotException {
        Command command = Parser.parse("delete 3");
        assertTrue(command instanceof DeleteCommand);
    }
    
    @Test
    public void testParseTodoCommand() throws TaskBotException {
        Command command = Parser.parse("todo read book");
        assertTrue(command instanceof AddCommand);
    }
    
    @Test
    public void testParseDeadlineCommand() throws TaskBotException {
        Command command = Parser.parse("deadline return book /by 2024-12-25");
        assertTrue(command instanceof AddCommand);
    }
    
    @Test
    public void testParseEventCommand() throws TaskBotException {
        Command command = Parser.parse("event project meeting /from 2pm /to 4pm");
        assertTrue(command instanceof AddCommand);
    }
    
    @Test
    public void testParseFindCommand() throws TaskBotException {
        Command command = Parser.parse("find book");
        assertTrue(command instanceof FindCommand);
    }
    
    @Test
    public void testParseInvalidCommand() {
        assertThrows(TaskBotException.class, () -> {
            Parser.parse("invalid command");
        });
    }
    
    @Test
    public void testParseEmptyTodo() {
        assertThrows(TaskBotException.class, () -> {
            Parser.parse("todo");
        });
    }
    
    @Test
    public void testParseEmptyDeadline() {
        assertThrows(TaskBotException.class, () -> {
            Parser.parse("deadline");
        });
    }
    
    @Test
    public void testParseInvalidDeadlineFormat() {
        assertThrows(TaskBotException.class, () -> {
            Parser.parse("deadline return book");
        });
    }
}