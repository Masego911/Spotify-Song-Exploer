import java.util.List; // used to store collections of data

/*
 * ETLPipeline
 *
 * PURPOSE:
 * Coordinates the full data ingestion process:
 * Extract → Transform → Load
 *
 * WHAT IT DOES:
 * 1. Reads raw CSV data (Extractor)
 * 2. Converts raw data into Song objects (Transformer)
 * 3. Inserts Song objects into database (Loader)
 *
 * WHY THIS EXISTS:
 * Separates data processing from graph logic.
 * Keeps the system modular and maintainable.
 */
public class ETLPipeline {

    public static void run(String filePath) throws Exception {

        // -----------------------------
        // STEP 1: EXTRACT
        // -----------------------------
        List<String[]> rawData = Extractor.extract(filePath);
        // rawData = list of rows, each row is an array of strings

        // -----------------------------
        // STEP 2: TRANSFORM
        // -----------------------------
        List<Song> songs = Transformer.transform(rawData);
        // converts raw strings into structured Song objects

        // -----------------------------
        // STEP 3: LOAD
        // -----------------------------
        Loader.load(songs);
        // inserts Song objects into database
    }
}
