import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestDataGenerator {
    public static void main(String[] args) throws IOException {
        generateTestDataCSV();
    }

    public static void generateTestDataCSV() throws IOException {
        List<String[]> dataLines = new ArrayList<>();
        dataLines.add(new String[] {"cookie", "timestamp"});
        for (int i = 1; i <= 1000000; i++) {
            dataLines.add(generateLine());
        }
        File csvOutputFile = new File("src/file1M.csv");
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            dataLines.stream()
                    .map(line -> convertToCSV(line))
                    .forEach(pw::println);
        }
    }

    private static String[] generateLine() {
        return new String[] { generateRandomString(), String.valueOf(OffsetDateTime.now())};
    }

    public static String convertToCSV(String[] data) {
        return Stream.of(data)
                .collect(Collectors.joining(","));
    }

    private static String generateRandomString() {
        String AlphaNumericString = "0000000111111aaaaAAAA";
        StringBuilder sb = new StringBuilder(5);

        for (int i = 0; i < 6; i++) {
            int index = (int)(AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }
}
