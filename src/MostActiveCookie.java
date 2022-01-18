import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class MostActiveCookie {
    private String mostActiveHolder;
    private Map<String, Integer> cookieFrequencies;
    private List<String> mostActiveCookies;

    private MostActiveCookie() {
        cookieFrequencies = new HashMap<>();
        mostActiveCookies = new ArrayList<>();
    }

    // time complexity O(n)
    // space complexity O(n)
    public static void main(String[] args) {
        try {
            if (Validator.isValid(args)) {
                FileReader reader = new FileReader(args[1]);
                BufferedReader buffer = new BufferedReader(reader);
                MostActiveCookie mostActiveCookie = new MostActiveCookie();
                mostActiveCookie.scanForDate(buffer, DateUtils.getDate(args[3]));
                mostActiveCookie.printMostActive();
            }
        } catch (IOException ioException) {
            System.out.println("File error. File not found or not accessible.");
        } catch (DateTimeParseException parseError) {
            System.out.println("Invalid data in cookie logs.");
        }
    }

    private void printMostActive() {
        if (!mostActiveCookies.isEmpty()) {
            mostActiveCookies.forEach(System.out::println);
        }
    }

    private void scanForDate(BufferedReader buffer, LocalDate date) throws IOException {
        String line = DateUtils.skipToDate(buffer, date);
        String[] lineData = line.split(Constants.SEPARATOR);
        while (Objects.nonNull(line) && DateUtils.parseDate(lineData[1]).isEqual(date)) {
            updateFrequency(line);
            line = buffer.readLine();
        }
        populateAllMostActive();
    }

    private void updateFrequency(String line) {
        String[] data = line.split(Constants.SEPARATOR);
        cookieFrequencies.put(data[0], cookieFrequencies.getOrDefault(data[0], 0) + 1);
        if (Objects.isNull(mostActiveHolder)) {
            mostActiveHolder = data[0];
        } else {
            if (isFrequentCookie(data[0])) {
                mostActiveHolder = data[0];
            }
        }
    }

    /**
     * Check if current cookie is frequenter than earlier saved
     */
    private boolean isFrequentCookie(String cookie) {
        return !mostActiveHolder.equals(cookie) && cookieFrequencies.get(cookie) > cookieFrequencies.get(mostActiveHolder);
    }

    private void populateAllMostActive() {
        if (Objects.nonNull(mostActiveHolder)) {
            int mostActiveFrequency = cookieFrequencies.get(mostActiveHolder);
            for (String cookie : cookieFrequencies.keySet()) {
                if (cookieFrequencies.get(cookie).equals(mostActiveFrequency)) {
                    mostActiveCookies.add(cookie);
                }
            }
        }
    }
}
