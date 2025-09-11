package JohnChatbot.Tasks;

import JohnChatbot.JohnChatbotException;

import java.io.Serializable;

public class Task implements Serializable {
    protected String description;
    protected boolean isDone;
    protected String tag;

    public Task(String description) throws JohnChatbotException {
        if (description.equals("")) {
            throw new JohnChatbotException("Need to add a name to the task");
        }
        this.description = description;
        this.isDone = false;
        this.tag = "[none]";
    }


    /**
     * Provides description of Task
     *
     * @return The description of the Task.
     */
    public String getDescription() {
        return this.description;
    }
    
    public boolean getStatus() {
        return this.isDone;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsUndone() {
        isDone = false;
    }

    public void setTag(String tagInput) {
        this.tag = tagInput;
    }

    @Override
    public String toString() {
        return "T | " + (isDone ? "1" : "0") + " | " + this.description + " | tag: " + this.tag;
    }
}
