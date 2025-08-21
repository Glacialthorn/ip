public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) throws JohnChatbotException {
        if (description.equals("")) {
            throw new JohnChatbotException("Need to add a name to the task");
        }
        this.description = description;
        this.isDone = false;
    }



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
    

}
