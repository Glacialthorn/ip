package JohnChatbot.Tasks;

import JohnChatbot.JohnChatbotException;

public class Todo extends Task {
    public Todo(String description) throws JohnChatbotException {
        super(description);
    }

    @Override
    public String toString() {
        if (this.isDone) {
            String output = "[T] [X] " + this.description + " | tag: " + this.tag;
            return output;
        } else {
            String output = "[T] [ ] " + this.description + " | tag: " + this.tag;
            return output;
        }
    }
}
