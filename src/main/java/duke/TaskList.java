package duke;

import duke.task.Task;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }
    
    public void add(Task task) {
        tasks.add(task);
    }
    
    public void delete(int index) {
        tasks.remove(index);
    }
    
    public Task get(int index) {
        return tasks.get(index);
    }
    
    public int size() {
        return tasks.size();
    }
    
    public ArrayList<Task> getTasks() {
        return tasks;
    }
    
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