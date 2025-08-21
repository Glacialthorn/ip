public class Deadline extends Task {
    public Deadline(String description) throws JohnChatbotException {
        super(description);
        int byIndex = description.indexOf("/by");
        String deadline = description.substring(byIndex + 3).trim();
        String finalDesc = description.substring(9, byIndex).trim() + " (by: " + deadline + ")";
        this.description = finalDesc;
    }

    @Override
    public String getDescription() {
        if (this.isDone) {
            String output = "[D] [X] " + this.description;
            return output;
        } else {
            String output = "[D] [ ] " + this.description;
            return output;
        }
    }
}
