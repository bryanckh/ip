package beechat.util;

import beechat.task.DeadlineTask;
import beechat.task.EventTask;
import beechat.task.Task;
import beechat.task.TodoTask;

import java.io.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private final String filePath;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy HHmm");

    public Storage(String filePath) {
        this.filePath = filePath;
    }
    public List<Task> loadTasks() throws IOException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                new File(file.getParent()).mkdirs(); // Create the directory if it doesn't exist
                file.createNewFile(); // Create the file if it doesn't exist
            } else {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(" \\| ");
                    String taskType = parts[0];
                    boolean isDone = parts[1].equals("1");
                    String description = parts[2];

                    switch (taskType) {
                        case "T":
                            Task todo = new TodoTask(description);
                            if (isDone) {
                                todo.mark();
                            }
                            tasks.add(todo);
                            break;
                        case "D":
                            try {
                                LocalDateTime by = LocalDateTime.parse(parts[3], FORMATTER);
                                Task deadline = new DeadlineTask(description, by);
                                if (isDone) deadline.mark();
                                tasks.add(deadline);
                            } catch (DateTimeParseException e) {
                                System.out.println("Error when parsing date for deadline task: " + e.getMessage());
                            }
                            break;
                        case "E":
                            try {
                                LocalDateTime from = LocalDateTime.parse(parts[3], FORMATTER);
                                LocalDateTime to = LocalDateTime.parse(parts[4], FORMATTER);
                                Task event = new EventTask(description, from, to);
                                if (isDone) event.mark();
                                tasks.add(event);
                            } catch (DateTimeParseException e) {
                                System.out.println("Error when parsing date for event task: " + e.getMessage());
                            }
                            break;
                        default:
                            throw new IOException("Invalid task type in file: " + taskType);
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    public void saveTasks(List<Task> tasks) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : tasks) {
                writer.write(task.toSaveFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
