package taskbot;

import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import taskbot.task.Task;
import taskbot.task.ToDo;
import taskbot.task.Deadline;
import taskbot.task.Event;

/**
 * Handles the storage and retrieval of tasks from a file.
 * Manages loading tasks from disk on startup and saving tasks to disk when modified.
 */
public class Storage {
    private static final String TASK_DELIMITER = " | ";
    private static final String TODO_MARKER = "T";
    private static final String DEADLINE_MARKER = "D";
    private static final String EVENT_MARKER = "E";
    private static final String DONE_MARKER = "1";
    private static final String NOT_DONE_MARKER = "0";
    
    private final String filePath;
    
    /**
     * Constructs a Storage object with the specified file path.
     * 
     * @param filePath the path to the file where tasks will be stored
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }
    
    /**
     * Loads tasks from the storage file.
     * Creates appropriate Task objects based on the stored data format.
     * 
     * @return ArrayList of Task objects loaded from the file
     * @throws TaskBotException if there is an error reading the file or parsing the data
     */
    public ArrayList<Task> load() throws TaskBotException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return tasks;
            }
            
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(TASK_DELIMITER);
                
                Task task = null;
                boolean isDone = parts[1].equals(DONE_MARKER);
                String description = parts[2];
                
                task = createTaskFromParts(parts, description);
                
                if (task != null) {
                    if (isDone) {
                        task.markAsDone();
                    }
                    tasks.add(task);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            throw new TaskBotException("File not found: " + filePath);
        } catch (Exception e) {
            throw new TaskBotException("Error loading tasks. File might be corrupted.");
        }
        return tasks;
    }
    
    private Task createTaskFromParts(String[] parts, String description) {
        String taskType = parts[0];
        
        switch (taskType) {
            case TODO_MARKER:
                return new ToDo(description);
            case DEADLINE_MARKER:
                final int deadlineIndex = 3;
                return new Deadline(description, parts[deadlineIndex]);
            case EVENT_MARKER:
                final int fromIndex = 3;
                final int toIndex = 4;
                return new Event(description, parts[fromIndex], parts[toIndex]);
            default:
                return null;
        }
    }
    
    /**
     * Saves the given list of tasks to the storage file.
     * Creates the necessary directories if they don't exist and writes tasks in the proper format.
     * 
     * @param tasks the list of tasks to save to the file
     * @throws TaskBotException if there is an error writing to the file
     */
    public void save(ArrayList<Task> tasks) throws TaskBotException {
        try {
            File dataDir = new File(filePath).getParentFile();
            if (dataDir != null && !dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                writer.write(formatTaskForStorage(task) + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new TaskBotException("Error saving tasks: " + e.getMessage());
        }
    }
    
    private String formatTaskForStorage(Task task) {
        StringBuilder sb = new StringBuilder();
        
        if (task instanceof ToDo) {
            sb.append(TODO_MARKER);
        } else if (task instanceof Deadline) {
            sb.append(DEADLINE_MARKER);
        } else if (task instanceof Event) {
            sb.append(EVENT_MARKER);
        }
        
        sb.append(TASK_DELIMITER)
          .append(task.isDone() ? DONE_MARKER : NOT_DONE_MARKER)
          .append(TASK_DELIMITER)
          .append(task.getDescription());
        
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            sb.append(TASK_DELIMITER).append(deadline.getBy());
        } else if (task instanceof Event) {
            Event event = (Event) task;
            sb.append(TASK_DELIMITER).append(event.getFrom())
              .append(TASK_DELIMITER).append(event.getTo());
        }
        
        return sb.toString();
    }
}
