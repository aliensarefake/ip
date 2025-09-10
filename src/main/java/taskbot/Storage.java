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
    private String filePath;
    
    /**
     * Constructs a Storage object with the specified file path.
     * 
     * @param filePath the path to the file where tasks will be stored
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.trim().isEmpty() : "File path cannot be null or empty";
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
                String[] parts = line.split(" \\| ");
                assert parts.length >= 3 : "Invalid task format in file: " + line;
                
                Task task = null;
                boolean isDone = parts[1].equals("1");
                String description = parts[2];
                
                if (parts[0].equals("T")) {
                    task = new ToDo(description);
                } else if (parts[0].equals("D")) {
                    final int deadlineIndex = 3;
                    task = new Deadline(description, parts[deadlineIndex]);
                } else if (parts[0].equals("E")) {
                    final int fromIndex = 3;
                    final int toIndex = 4;
                    task = new Event(description, parts[fromIndex], parts[toIndex]);
                }
                
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
    
    /**
     * Saves the given list of tasks to the storage file.
     * Creates the necessary directories if they don't exist and writes tasks in the proper format.
     * 
     * @param tasks the list of tasks to save to the file
     * @throws TaskBotException if there is an error writing to the file
     */
    public void save(ArrayList<Task> tasks) throws TaskBotException {
        assert tasks != null : "Tasks list cannot be null";
        try {
            File dataDir = new File(filePath).getParentFile();
            if (dataDir != null && !dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                assert task != null : "Task in list cannot be null";
                String line = "";
                
                if (task instanceof ToDo) {
                    line = "T | ";
                } else if (task instanceof Deadline) {
                    line = "D | ";
                } else if (task instanceof Event) {
                    line = "E | ";
                }
                
                line += (task.isDone() ? "1" : "0") + " | " + task.getDescription();
                
                if (task instanceof Deadline) {
                    line += " | " + ((Deadline) task).getBy();
                } else if (task instanceof Event) {
                    line += " | " + ((Event) task).getFrom() + " | " + ((Event) task).getTo();
                }
                
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new TaskBotException("Error saving tasks: " + e.getMessage());
        }
    }
}
