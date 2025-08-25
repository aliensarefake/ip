package taskbot;

import taskbot.task.Task;
import java.util.ArrayList;

/**
 * Manages a list of tasks and provides operations to manipulate them.
 * Supports adding, deleting, retrieving, and searching tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;
    
    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    
    /**
     * Constructs a TaskList with the given list of tasks.
     * 
     * @param tasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    
    /**
     * Adds a task to the task list.
     * 
     * @param task the task to be added
     */
    public void add(Task task) {
        tasks.add(task);
    }
    
    /**
     * Deletes a task at the specified index.
     * 
     * @param index the index of the task to be deleted
     */
    public void delete(int index) {
        tasks.remove(index);
    }
    
    /**
     * Retrieves the task at the specified index.
     * 
     * @param index the index of the task to retrieve
     * @return the task at the specified index
     */
    public Task get(int index) {
        return tasks.get(index);
    }
    
    /**
     * Returns the number of tasks in the list.
     * 
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }
    
    /**
     * Returns the underlying ArrayList of tasks.
     * 
     * @return the ArrayList containing all tasks
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    
    /**
     * Finds tasks that contain the specified keyword in their description.
     * The search is case-insensitive.
     * 
     * @param keyword the keyword to search for
     * @return ArrayList of tasks matching the keyword
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.description.toLowerCase().contains(keyword.toLowerCase())) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
