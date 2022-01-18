import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Validator {
    public static boolean isValid(final String[] args) throws IOException {
        return validateArguments(args) && fileContentValidator(args);
    }

    private static boolean validateArguments(final String[] args) {
        boolean invalid = true;
        if (        Objects.isNull(args)
                ||  args.length != 4
                ||  !args[0].equals("-f")
                ||  args[1].length() < 5
                ||  !args[1].substring(args[1].length() - 4).equals(".csv")
                ||  !args[2].equals("-d")
                ||  !args[3].matches("\\d{4}-\\d{2}-\\d{2}")) {
            System.out.println("Fewer/incorrect parameters. Expected run command format is: ./[command] -f cookie_log.csv -d 2018-12-09");
            invalid = false;
        }
        return invalid;
    }

    private static boolean fileContentValidator(final String[] args) throws IOException {
        FileReader reader = new FileReader(args[1]);
        BufferedReader buffer = new BufferedReader(reader);
        boolean invalid = true;
        String header = buffer.readLine();
        if (Objects.isNull(header)) {
            System.out.println("Empty file.");
            invalid = false;
        } else {
            String[] headers = header.split(",");
            if (headers.length < 2 || !headers[0].equals("cookie") || !headers[1].equals("timestamp")) {
                System.out.println("File headers are missing.");
                invalid = false;
            }
        }
        String firstLine = buffer.readLine();
        if (Objects.isNull(firstLine) || firstLine.length() < 2) {
            System.out.println("No cookie data found.");
            invalid = false;
        }
        return invalid;
    }
}
