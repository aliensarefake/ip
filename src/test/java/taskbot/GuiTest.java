package taskbot;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javafx.scene.image.Image;

public class GuiTest {
    private TaskBot taskBot;

    @BeforeEach
    public void setUp() {
        taskBot = new TaskBot("data/test-tasks.txt");
    }

    @Test
    public void testTaskBotInitialization() {
        assertNotNull(taskBot);
    }

    @Test
    public void testGetResponse() {
        String response = taskBot.getResponse("list");
        assertNotNull(response);
        assertTrue(response.contains("Here are the tasks in your list") ||
                  response.contains("No tasks in your list"));
    }

    @Test
    public void testErrorResponse() {
        String response = taskBot.getResponse("invalid command");
        assertNotNull(response);
        assertTrue(taskBot.isErrorResponse(response));
    }

    @Test
    public void testAddTodoCommand() {
        String response = taskBot.getResponse("todo Test task");
        assertNotNull(response);
        assertTrue(response.contains("added"));
    }

}