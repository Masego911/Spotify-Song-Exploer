import java.util.ArrayList;
import java.util.List;

public class Transformer {

    public static List<Song> transform(List<String[]> rawData) {

        List<Song> songs = new ArrayList<>();

        if (rawData == null || rawData.isEmpty()) {
            System.out.println("ERROR: No data loaded.");
            return songs;
        }

        String[] header = rawData.get(0);

        System.out.println("HEADER:");
        System.out.println(java.util.Arrays.toString(header));

        int titleIndex = findIndex(header, "track");
        if (titleIndex == -1) titleIndex = findIndex(header, "name");

        int artistIndex = findIndex(header, "artist");
        int streamsIndex = findIndex(header, "spotify streams"); // more precise
        int genreIndex = findIndex(header, "genre"); // may be -1

        System.out.println("\nIndexes:");
        System.out.println("title: " + titleIndex);
        System.out.println("artist: " + artistIndex);
        System.out.println("streams: " + streamsIndex);
        System.out.println("genre: " + genreIndex);

        if (titleIndex == -1 || artistIndex == -1 || streamsIndex == -1) {
            System.out.println("ERROR: Required columns not found.");
            return songs;
        }

        int id = 1;

        for (int i = 1; i < rawData.size(); i++) {

            String[] row = rawData.get(i);

            try {

                if (row.length <= Math.max(titleIndex,
                        Math.max(artistIndex, streamsIndex))) {
                    continue;
                }

                String title = clean(row[titleIndex]);
                String artist = clean(row[artistIndex]);

                String streamStr = clean(row[streamsIndex]).replaceAll("[^0-9]", "");

                if (streamStr.isEmpty()) continue;

                long streams = Long.parseLong(streamStr);

                // -----------------------------
                // GENRE EXTRACTION
                // -----------------------------
                String genre = "Unknown";

                if (genreIndex != -1 && row.length > genreIndex) {
                    genre = clean(row[genreIndex]);
                    if (genre.isEmpty()) genre = "Unknown";
                }

                Song song = new Song(id++, title, artist, streams, genre);

                songs.add(song);

            } catch (Exception e) {

                System.out.println("Skipping row: " + java.util.Arrays.toString(row));
                System.out.println("Reason: " + e.getMessage());
            }
        }

        return songs;
    }

    private static int findIndex(String[] header, String keyword) {

        for (int i = 0; i < header.length; i++) {

            String col = header[i].toLowerCase().trim();

            if (col.contains(keyword)) return i;

            if (keyword.equals("track") && col.contains("name")) return i;
        }

        return -1;
    }

    private static String clean(String value) {
        return value.replace("\"", "").trim();
    }
}