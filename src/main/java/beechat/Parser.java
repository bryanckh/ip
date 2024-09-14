package beechat;

public class Parser {

    public static Command parse(String fullCommand) throws Exception {
        String[] words = fullCommand.split(" ", 2);
        String commandWord = words[0];

        switch (commandWord) {
            case "bye":
                return new LeaveCommand();
            case "list":
                return new ListCommand();
            case "mark":
                return new MarkCommand(Integer.parseInt(words[1]));
            case "unmark":
                return new UnmarkCommand(Integer.parseInt(words[1]));
            case "delete":
                return new DeleteCommand(Integer.parseInt(words[1]));
            case "todo":
                return new AddCommand(fullCommand);
            case "deadline":
                return new AddCommand(fullCommand);
            case "event":
                return new AddCommand(fullCommand);
            default:
                throw new BeeException("I'm sorry, but I don't know what that means :-(");
        }
    }
}
