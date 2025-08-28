package JohnChatbot.Tasks;

import JohnChatbot.JohnChatbotException;
import JohnChatbot.Parser;

import java.time.LocalDateTime;

public class Deadline extends Task {
    private LocalDateTime deadline;

    public Deadline(String description, LocalDateTime deadline) throws JohnChatbotException {
        super(description);
        this.description = description;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        if (this.isDone) {
            String output = "[D] [X] " + this.description + " " + Parser.dateTimeToString(deadline);
            return output;
        } else {
            String output = "[D] [ ] " + this.description + " " + Parser.dateTimeToString(deadline);
            return output;
        }
    }
}
