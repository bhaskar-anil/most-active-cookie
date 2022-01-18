import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateUtils {
    public static LocalDate getDate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ofPattern(Constants.DATE_FORMAT));
    }
    public static LocalDate parseDate(String offsetDateString) {
        return OffsetDateTime.parse(offsetDateString).toLocalDate();
    }

    /**
     * Skips to the given date's cookie record
     */
    public static String skipToDate(BufferedReader buffer, LocalDate date) throws IOException {
        String line = buffer.readLine();
        line = buffer.readLine(); // skip the header line

        while (Objects.nonNull(line) && parseDate(line.split(",")[1]).isBefore(date)) {
            line = buffer.readLine();
        }
        return line;
    }
}
