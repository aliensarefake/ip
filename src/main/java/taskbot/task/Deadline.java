package taskbot.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a deadline.
 */
public class Deadline extends Task {
    public String by;
    protected LocalDate date;
    protected LocalDateTime dateTime;

    /**
     * Creates a new deadline task.
     * 
     * @param description the task description
     * @param by the deadline date/time string
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
        parseDateTime(by);
    }
    
    /**
     * Parses the date/time string into LocalDate or LocalDateTime.
     * Supports formats: yyyy-mm-dd HHmm, yyyy-mm-dd
     * 
     * @param dateStr the date/time string to parse
     */
    private void parseDateTime(String dateStr) {
        // Try parsing as LocalDateTime first (yyyy-mm-dd HHmm format)
        try {
            String[] parts = dateStr.split(" ");
            if (parts.length == 2 && parts[1].length() == 4) {
                String formattedDateTime = parts[0] + "T" + parts[1].substring(0, 2) + ":" + parts[1].substring(2);
                this.dateTime = LocalDateTime.parse(formattedDateTime);
                this.date = null;
                return;
            }
        } catch (Exception e) {
            // Continue to next format
        }
        
        // Try parsing as standard ISO date (yyyy-mm-dd)
        try {
            this.date = LocalDate.parse(dateStr);
            this.dateTime = null;
        } catch (DateTimeParseException e) {
            this.date = null;
            this.dateTime = null;
        }
    }

    @Override
    public String toString() {
        String dateStr = by;
        if (dateTime != null) {
            dateStr = dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"));
        } else if (date != null) {
            dateStr = date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        }
        return "[D]" + super.toString() + " (by: " + dateStr + ")";
    }
}
