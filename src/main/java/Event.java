import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected String from;
    protected String to;
    protected LocalDateTime fromDateTime;
    protected LocalDateTime toDateTime;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        try {
            this.fromDateTime = LocalDateTime.parse(from, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            this.fromDateTime = null;
        }
        try {
            this.toDateTime = LocalDateTime.parse(to, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            this.toDateTime = null;
        }
    }

    @Override
    public String toString() {
        String fromStr = from;
        String toStr = to;
        if (fromDateTime != null) {
            fromStr = fromDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"));
        }
        if (toDateTime != null) {
            toStr = toDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy HH:mm"));
        }
        return "[E]" + super.toString() + " (from: " + fromStr + " to: " + toStr + ")";
    }
}