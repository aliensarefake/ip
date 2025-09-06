package taskbot.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents an event task with start and end times.
 */
public class Event extends Task {
    private String from;
    private String to;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    /**
     * Creates a new event task.
     * 
     * @param description the task description
     * @param from the start date/time string
     * @param to the end date/time string
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
        this.fromDateTime = parseDateTime(from);
        this.toDateTime = parseDateTime(to);
    }
    
    /**
     * Parses the date/time string into LocalDateTime.
     * Supports formats: yyyy-mm-dd HHmm, ISO format, yyyy-mm-dd
     * 
     * @param dateStr the date/time string to parse
     * @return LocalDateTime or null if parsing fails
     */
    private LocalDateTime parseDateTime(String dateStr) {
        // Try parsing as yyyy-mm-dd HHmm format
        try {
            String[] parts = dateStr.split(" ");
            final int timeLength = 4;
            if (parts.length == 2 && parts[1].length() == timeLength) {
                String formattedDateTime = parts[0] + "T" + parts[1].substring(0, 2) + ":" + parts[1].substring(2);
                return LocalDateTime.parse(formattedDateTime);
            }
        } catch (Exception e) {
            // Continue to next format
        }
        
        // Try standard ISO format
        try {
            return LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
            // Continue to next format
        }
        
        // Try date-only format (assume start of day)
        try {
            return LocalDateTime.parse(dateStr + "T00:00:00");
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        String fromStr = from;
        String toStr = to;
        if (fromDateTime != null) {
            fromStr = fromDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"));
        }
        if (toDateTime != null) {
            toStr = toDateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"));
        }
        return "[E]" + super.toString() + " (from: " + fromStr + " to: " + toStr + ")";
    }
    
    public String getFrom() {
        return from;
    }
    
    public String getTo() {
        return to;
    }
}
