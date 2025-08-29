package seedu.JohnChatbot;

import JohnChatbot.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ParserTest {
    @Test
    public void getFlag_validInput_returnsCorrectDescription() {
        String input = "todo read book";
        String description = Parser.getFlag(input, "todo");
        assertEquals("read book", description);
    }

    @Test
    public void getFlag_invalidFlag_returnsEmptyString() {
        String input = "todo read book";
        String description = Parser.getFlag(input, "/from");
        assertTrue(description.isEmpty());
    }

    @Test
    public void getFlag_withEmptyDescription_returnsEmptyString() {
        String input = "event /from 2025-08-29 1000 /to 2025-08-29 1040";
        String description = Parser.getFlag(input, "event");
        assertTrue(description.isEmpty());
    }
}