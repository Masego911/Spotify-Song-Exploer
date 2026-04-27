import java.io.BufferedReader; // read file line by line
import java.io.FileReader;     // open file
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

        List<String[]> data = new ArrayList<>(); // store all rows

        BufferedReader br = new BufferedReader(new FileReader(filePath)); // open file

        String line;

        while ((line = br.readLine()) != null) {

            // split only on commas OUTSIDE quotes
            String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

            data.add(values); // store row
        }

        br.close(); // close file

        return data; // return all rows
    }
}