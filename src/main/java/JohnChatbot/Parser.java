package JohnChatbot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

public class Parser {
    public static final List<String> formatList = List.of("yyyy-MM-dd HHmm", "HHmm yyyy-MM-dd");
    private static final DateTimeFormatter formatter = dtFromList(formatList);

    public static DateTimeFormatter dtFromList(List<String> list) {
        DateTimeFormatterBuilder dtFBuilder = new DateTimeFormatterBuilder();
        list.forEach(input -> dtFBuilder.appendOptional(DateTimeFormatter.ofPattern(input)));
        return dtFBuilder.toFormatter();
    }

    public static String dateTimeToString(LocalDateTime formatter) {
        return formatter.format(DateTimeFormatter.ofPattern("MM dd yyyy, HHmm")) + "H";
    }

    public static LocalDateTime parseDateTime(String str) {
        return LocalDateTime.parse(str, formatter);
    }

    /**
     * Returns input with the flag removed
     *
     * @param input The input string which contains: flag + rest of command
     * @param flag The string to search for and remove.
     * @return The input string with the flag removed.
     */
    public static String getFlag(String input, String flag) {
        if (input.contains(flag)) {
            return input.split(flag, 2)[1].split("/", 2)[0].trim();
        } else {
            return "";
        }
    }
}
