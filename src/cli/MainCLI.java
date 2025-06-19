package cli;

import core.TaskRepository;
import core.TaskService;
import mapper.TaskMapper;

import java.nio.file.Path;

/**
 * Author: abdallah-issakass
 */
public class MainCLI {
    public static void main(String[] args) {
        Path path = Path.of("tasks.json");
        TaskMapper mapper = new TaskMapper();
        TaskRepository repository = new TaskRepository(path, mapper);
        TaskService service = new TaskService(repository);
        TaskCLI cli = new TaskCLI(service);
        cli.run(args);
    }
}
