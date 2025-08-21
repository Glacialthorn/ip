public class Event extends Task {

    public Event(String description) throws JohnChatbotException {
        super(description);
        int fromIndex = description.indexOf("/from");
        int toIndex = description.indexOf("/to");
        String eventName = description.substring(Math.min(description.length(), 6), fromIndex).trim();
        String startTime = description.substring(fromIndex + 5, toIndex).trim();
        String endTime = description.substring(toIndex + 3).trim();
        String finalDesc = eventName + " (from: " + startTime + " to: " + endTime + ")";
        this.description = finalDesc;
    }

    @Override
    public String getDescription() {
        if (this.isDone) {
            String output = "[E] [X] " + this.description;
            return output;
        } else {
            String output = "[E] [ ] " + this.description;
            return output;
        }
    }
}
