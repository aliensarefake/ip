package taskbot;

import taskbot.command.Command;

/**
 * Main class for the TaskBot task management application.
 * Handles the initialization and execution of the task bot.
 */
public class TaskBot {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new TaskBot instance with the specified file path for data storage.
     * Initializes UI, storage, and attempts to load existing tasks.
     * 
     * @param filePath the file path for storing and loading tasks
     */
    public TaskBot(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (TaskBotException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the TaskBot application.
     * Continuously reads user commands, parses them, and executes the appropriate actions
     * until the user exits the application.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (TaskBotException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
        ui.close();
    }

    /**
     * Generates a response for the user's input.
     * Parses the input command and executes it, returning the result as a string.
     *
     * @param input the user's input command
     * @return the response from executing the command
     */
    public String getResponse(String input) {
        try {
            Command c = Parser.parse(input);
            return c.executeAndGetResponse(tasks, storage);
        } catch (TaskBotException e) {
            return e.getMessage();
        }
    }

    /**
     * Checks if a response is an error message.
     *
     * @param response the response to check
     * @return true if the response is an error, false otherwise
     */
    public boolean isErrorResponse(String response) {
        return response.startsWith("☹ OOPS!!!") || response.contains("OOPS");
    }
    
    /**
     * Main entry point for the TaskBot application.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new TaskBot("data/tasks.txt").run();
    }
}
