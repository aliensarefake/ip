package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.TaskBotException;
import taskbot.Ui;
import taskbot.task.Task;
import taskbot.task.Deadline;
import taskbot.task.Event;
import taskbot.task.ToDo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a command to sort tasks by various criteria.
 */
public class SortCommand extends Command {
    public enum SortCriteria {
        DESCRIPTION, STATUS, TYPE, DATE
    }
    
    private final SortCriteria criteria;
    
    /**
     * Creates a new sort command with the specified criteria.
     * 
     * @param criteria the sort criteria to use
     */
    public SortCommand(SortCriteria criteria) {
        this.criteria = criteria;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TaskBotException {
        String response = executeAndGetResponse(tasks, storage);
        ui.showLine();
        ui.showError(response);
        ui.showLine();
    }
    
    @Override
    public String executeAndGetResponse(TaskList tasks, Storage storage) throws TaskBotException {
        if (tasks.size() == 0) {
            return "No tasks to sort!";
        }
        
        ArrayList<Task> taskList = tasks.getTasks();
        sortTasks(taskList);
        
        storage.save(taskList);
        
        return buildSortedResponse(taskList);
    }
    
    private void sortTasks(List<Task> taskList) {
        Comparator<Task> comparator = getComparator();
        taskList.sort(comparator);
    }
    
    private Comparator<Task> getComparator() {
        switch (criteria) {
            case DESCRIPTION:
                return Comparator.comparing(Task::getDescription, String.CASE_INSENSITIVE_ORDER);
            case STATUS:
                return Comparator.comparing(Task::isDone)
                        .thenComparing(Task::getDescription, String.CASE_INSENSITIVE_ORDER);
            case TYPE:
                return Comparator.comparing(this::getTaskTypePriority)
                        .thenComparing(Task::getDescription, String.CASE_INSENSITIVE_ORDER);
            case DATE:
                return Comparator.comparing(this::getTaskDatePriority)
                        .thenComparing(Task::getDescription, String.CASE_INSENSITIVE_ORDER);
            default:
                return Comparator.comparing(Task::getDescription, String.CASE_INSENSITIVE_ORDER);
        }
    }
    
    private int getTaskTypePriority(Task task) {
        if (task instanceof Deadline) return 1;
        if (task instanceof Event) return 2;
        if (task instanceof ToDo) return 3;
        return 4;
    }
    
    private long getTaskDatePriority(Task task) {
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            try {
                java.time.LocalDate date = java.time.LocalDate.parse(deadline.getBy());
                return date.toEpochDay();
            } catch (Exception e) {
                return Long.MAX_VALUE;
            }
        }
        if (task instanceof Event) {
            Event event = (Event) task;
            try {
                java.time.LocalDate date = java.time.LocalDate.parse(event.getFrom().split(" ")[0]);
                return date.toEpochDay();
            } catch (Exception e) {
                return Long.MAX_VALUE;
            }
        }
        return Long.MAX_VALUE;
    }
    
    private String buildSortedResponse(ArrayList<Task> taskList) {
        StringBuilder response = new StringBuilder();
        response.append("Tasks sorted by ").append(criteria.toString().toLowerCase()).append(":\n");
        
        for (int i = 0; i < taskList.size(); i++) {
            response.append(i + 1).append(". ").append(taskList.get(i)).append("\n");
        }
        
        return response.toString().trim();
    }
}