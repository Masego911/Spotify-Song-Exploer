import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.nio.charset.StandardCharsets;
import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;    // dynamic list
import java.util.List;         // list interface

/*
 * EXTRACTOR
 *
 * PURPOSE:
 * Reads CSV file correctly.
 *
 * FIX:
 * Uses regex to ignore commas inside quotes.
 * DOES NOT skip header (Transformer needs it).
 */
public class Extractor {

    public static List<String[]> extract(String filePath) throws Exception {
        Path path = Path.of(filePath);
        try {
            return extract(path, StandardCharsets.UTF_8);
        } catch (MalformedInputException e) {
            return extract(path, Charset.forName("windows-1252"));
        }
    }

    private static List<String[]> extract(Path path, Charset charset) throws IOException {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader buffered = Files.newBufferedReader(path, charset);
             PushbackReader reader = new PushbackReader(buffered, 1)) {
            List<String> row = new ArrayList<>();
            StringBuilder field = new StringBuilder();
            boolean quoted = false;
            int value;

            while ((value = reader.read()) != -1) {
                char current = (char) value;
                if (current == '"') {
                    if (quoted) {
                        int next = reader.read();
                        if (next == '"') {
                            field.append('"');
                        } else {
                            quoted = false;
                            if (next != -1) reader.unread(next);
                        }
                    } else if (field.length() == 0) {
                        quoted = true;
                    } else {
                        field.append(current);
                    }
                } else if (current == ',' && !quoted) {
                    row.add(field.toString());
                    field.setLength(0);
                } else if ((current == '\n' || current == '\r') && !quoted) {
                    if (current == '\r') {
                        int next = reader.read();
                        if (next != '\n' && next != -1) reader.unread(next);
                    }
                    row.add(field.toString());
                    field.setLength(0);
                    data.add(row.toArray(new String[0]));
                    row = new ArrayList<>();
                } else {
                    field.append(current);
                }
            }

            if (quoted) {
                throw new IOException("Unclosed quoted field in CSV file: " + path);
            }
            if (field.length() > 0 || !row.isEmpty()) {
                row.add(field.toString());
                data.add(row.toArray(new String[0]));
            }
        }
        return data;
    }
}
