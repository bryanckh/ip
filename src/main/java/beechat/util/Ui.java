package beechat.util;

import beechat.task.TaskList;

import java.util.Scanner;

/**
 * Handles the user interface.
 * Displays messages to the user and reads user input.
 */
public class Ui {

    /** Scanner to read user input from the command line. */
    private final Scanner sc;

    /** Stores the latest response to be displayed in the GUI. */
    private String latestResponse;

    /**
     * Constructs a Ui object and initializes the scanner for reading user input.
     */
    public Ui() {
        sc = new Scanner(System.in);
        latestResponse = "";  // Initialize with an empty response
    }

    /**
     * Returns the welcome message when the application starts.
     *
     * @return The welcome message.
     */
    public String getWelcomeMessage() {
        return "Hello! I'm Beechat!\nWhat can I do for you?\n";
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        latestResponse = "Hello! I'm Beechat!\nWhat can I do for you?\n";
        System.out.println(latestResponse);
    }

    /**
     * Reads the user's input.
     *
     * @return The user's input as a String.
     */
    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Displays an error message if there was an error loading the task list.
     */
    public void showLoadingError() {
        latestResponse = "There was an error loading your task list.";
        System.out.println(latestResponse);
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to be displayed.
     */
    public void showError(String message) {
        latestResponse = message;
        System.out.println(message);
    }

    /**
     * Displays the bye message when the user is leaving the application.
     */
    public void showBye() {
        latestResponse = "Bye. Hope to see you again soon!";
        System.out.println(latestResponse);
    }

    /**
     * Displays the bye message when the user is leaving the application.
     */
    public void showHelp() {
        latestResponse = "Help\n\n Add tasks using todo, deadline or event\n" +
                "Delete tasks using delete <index>\n" +
                "Find tasks using find <keyword>\n" +
                "Leave using bye\n" +
                "List tasklist using list\n" +
                "Mark/unmark tasks as done using mark/unmark <index>";
        System.out.println(latestResponse);
    }

    /**
     * Displays all tasks in the task list to the user.
     *
     * @param tasks The TaskList object containing all tasks.
     */
    public void showTasks(TaskList tasks) {
        if (tasks.isEmpty()) {
            latestResponse = "No tasks so far";
        } else {
            StringBuilder sb = new StringBuilder("Here are all the tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(i + 1).append(".").append(tasks.getTask(i)).append("\n");
            }
            latestResponse = sb.toString();
        }
        System.out.println(latestResponse);
    }

    /**
     * Displays all matching tasks in the TaskList to the user.
     *
     * @param tasks The list of all matching tasks.
     */
    public void showFindResults(TaskList tasks) {
        if (tasks.isEmpty()) {
            latestResponse = "No matching tasks so far";
        } else {
            StringBuilder sb = new StringBuilder("Here are all the matching tasks in your list:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append(i + 1).append(".").append(tasks.getTask(i)).append("\n");
            }
            latestResponse = sb.toString();
        }
        System.out.println(latestResponse);
    }

    /**
     * Gets the latest response generated by the Ui.
     *
     * @return The latest response.
     */
    public String getLatestResponse() {
        return latestResponse;
    }
}
