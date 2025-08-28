import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;

public class Parser {
    public static final List<String> formatList = List.of("yyyy-MM-dd HHmm", "HHmm yyyy-MM-dd");
    private static final DateTimeFormatter dtFormatter = dtFormatterFromList(formatList);

    public static DateTimeFormatter dtFormatterFromList(List<String> list) {
        DateTimeFormatterBuilder dtFBuilder = new DateTimeFormatterBuilder();
        list.forEach(str -> dtFBuilder.appendOptional(DateTimeFormatter.ofPattern(str)));
        return dtFBuilder.toFormatter();
    }

    public static String dateTimeToString(LocalDateTime dTF) {
        return dTF.format(DateTimeFormatter.ofPattern("MMM dd yyyy, HHmm")) +"H";
    }

    public static LocalDateTime parseDateTime(String str) {
        return LocalDateTime.parse(str, dtFormatter);
    }

    public static String getFlag(String line, String flag) {
        if (line.contains(flag)) {
            return line.split(flag, 2)[1].split("/", 2)[0].trim();
        } else {
            return "";
        }
    }
}
