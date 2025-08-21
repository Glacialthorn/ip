public class Todo extends Task {
    public Todo(String description) throws JohnChatbotException{
        super(description);
    }


    @Override
    public String getDescription() {
        if (this.isDone) {
            String output = "[T] [X] " + this.description;
            return output;
        } else {
            String output = "[T] [ ] " + this.description;
            return output;
        }
    }
}
