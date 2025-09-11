package JohnChatbot.Tasks;

import JohnChatbot.JohnChatbotException;

import java.time.LocalDateTime;

public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    public Event(String description, LocalDateTime start, LocalDateTime end) throws JohnChatbotException {
        super(description);
        this.description = description;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        if (this.isDone) {
            String output = "[E] [X] " + this.description + " | tag: " + this.tag;
            return output;
        } else {
            String output = "[E] [ ] " + this.description + " | tag: " + this.tag;
            return output;
        }
    }
}
