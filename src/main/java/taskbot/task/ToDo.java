package taskbot.task;

/**
 * Represents a todo task without any date/time attached.
 */
public class ToDo extends Task {
    /**
     * Creates a new todo task with the given description.
     * 
     * @param description the task description
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
