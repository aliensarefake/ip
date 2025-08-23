public class AddCommand extends Command {
    private Task task;
    
    public AddCommand(Task task) {
        this.task = task;
    }
    
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws TaskBotException {
        tasks.add(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.size());
    }
}