public enum Command {
    BYE,
    LIST,
    MARK,
    UNMARK,
    DELETE,
    TODO,
    DEADLINE,
    EVENT,
    UNKNOWN;

    public static Command parseCommand(String input) {
        if (input.equals("bye")) return BYE;
        if (input.equals("list")) return LIST;
        if (input.startsWith("mark ")) return MARK;
        if (input.startsWith("unmark ")) return UNMARK;
        if (input.startsWith("delete ")) return DELETE;
        if (input.startsWith("todo") || input.equals("todo")) return TODO;
        if (input.startsWith("deadline") || input.equals("deadline")) return DEADLINE;
        if (input.startsWith("event") || input.equals("event")) return EVENT;
        return UNKNOWN;
    }
}