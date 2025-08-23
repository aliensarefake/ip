package duke;

import duke.task.*;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import duke.task.Task;
import duke.task.ToDo;
import duke.task.Deadline;
import duke.task.Event;

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
                
                Task task = null;
                boolean isDone = parts[1].equals("1");
                String description = parts[2];
                
                if (parts[0].equals("T")) {
                    task = new ToDo(description);
                } else if (parts[0].equals("D")) {
                    task = new Deadline(description, parts[3]);
                } else if (parts[0].equals("E")) {
                    task = new Event(description, parts[3], parts[4]);
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
        try {
            File dataDir = new File(filePath).getParentFile();
            if (dataDir != null && !dataDir.exists()) {
                dataDir.mkdirs();
            }
            
            FileWriter writer = new FileWriter(filePath);
            for (Task task : tasks) {
                String line = "";
                
                if (task instanceof ToDo) {
                    line = "T | ";
                } else if (task instanceof Deadline) {
                    line = "D | ";
                } else if (task instanceof Event) {
                    line = "E | ";
                }
                
                line += (task.isDone ? "1" : "0") + " | " + task.description;
                
                if (task instanceof Deadline) {
                    line += " | " + ((Deadline) task).by;
                } else if (task instanceof Event) {
                    line += " | " + ((Event) task).from + " | " + ((Event) task).to;
                }
                
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new TaskBotException("Error saving tasks: " + e.getMessage());
        }
    }
}
