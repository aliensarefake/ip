package duke;

import duke.command.Command;

/**
 * Main class for the Duke task management application.
 * Handles the initialization and execution of the task bot.
 */
public class Duke {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Duke instance with the specified file path for data storage.
     * Initializes UI, storage, and attempts to load existing tasks.
     * 
     * @param filePath the file path for storing and loading tasks
     */
    public Duke(String filePath) {
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
     * Runs the main loop of the Duke application.
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
     * Main entry point for the Duke application.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new Duke("data/tasks.txt").run();
    }
}