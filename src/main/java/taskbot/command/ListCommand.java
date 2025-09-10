package taskbot.command;

import taskbot.Storage;
import taskbot.TaskList;
import taskbot.Ui;
import java.util.stream.IntStream;
import java.util.stream.Collectors;

public class ListCommand extends Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showTaskList(tasks);
    }
    
    @Override
    public String executeAndGetResponse(TaskList tasks, Storage storage) {
        if (tasks.size() == 0) {
            return "No tasks in your list yet.";
        }
        String header = "Here are the tasks in your list:\n";
        String taskList = IntStream.range(0, tasks.size())
            .mapToObj(i -> (i + 1) + "." + tasks.get(i))
            .collect(Collectors.joining("\n"));
        return header + taskList;
    }
}
