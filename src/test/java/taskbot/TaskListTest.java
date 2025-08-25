package taskbot;

import taskbot.task.Task;
import taskbot.task.ToDo;
import taskbot.task.Deadline;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class TaskListTest {
    private TaskList taskList;
    
    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
    }
    
    @Test
    public void testAddTask() {
        Task todo = new ToDo("test task");
        taskList.add(todo);
        assertEquals(1, taskList.size());
        assertEquals(todo, taskList.get(0));
    }
    
    @Test
    public void testDeleteTask() {
        Task todo1 = new ToDo("task 1");
        Task todo2 = new ToDo("task 2");
        taskList.add(todo1);
        taskList.add(todo2);
        assertEquals(2, taskList.size());
        
        taskList.delete(0);
        assertEquals(1, taskList.size());
        assertEquals(todo2, taskList.get(0));
    }
    
    @Test
    public void testFindTasks() {
        Task todo = new ToDo("read book");
        Task deadline = new Deadline("return book", "2024-12-25");
        Task todo2 = new ToDo("buy groceries");
        
        taskList.add(todo);
        taskList.add(deadline);
        taskList.add(todo2);
        
        ArrayList<Task> results = taskList.find("book");
        assertEquals(2, results.size());
        assertTrue(results.contains(todo));
        assertTrue(results.contains(deadline));
        assertFalse(results.contains(todo2));
    }
    
    @Test
    public void testEmptyTaskList() {
        assertEquals(0, taskList.size());
        ArrayList<Task> results = taskList.find("anything");
        assertEquals(0, results.size());
    }
}