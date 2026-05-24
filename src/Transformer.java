import java.util.ArrayList;
import java.util.List;

public class Transformer {

    public static List<Song> transform(List<String[]> rawData) {

        List<Song> songs = new ArrayList<>();

        if (rawData == null || rawData.isEmpty()) return songs;

        String[] header = rawData.get(0);

        int titleIndex = findIndex(header, "track", "track name", "name", "title");
        int artistIndex = findIndex(header, "artist", "artist name");
        int streamsIndex = findIndex(header, "spotify streams", "streams");
        int genreIndex = findIndex(header, "genre");

        if (titleIndex == -1 || artistIndex == -1 || streamsIndex == -1) {
            throw new IllegalArgumentException("CSV requires track, artist, and Spotify Streams columns");
        }

        for (int i = 1; i < rawData.size(); i++) {

            String[] row = rawData.get(i);

            try {

                if (row.length <= Math.max(titleIndex,
                        Math.max(artistIndex, streamsIndex))) {
                    continue;
                }

                String title = clean(row[titleIndex]);
                String artist = clean(row[artistIndex]);
                if (title.isEmpty() || artist.isEmpty()) continue;

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

                Song song = new Song(i, title, artist, streams, genre);

                songs.add(song);

            } catch (NumberFormatException e) {
                // Rows without a valid Spotify stream count cannot be ranked.
            }
        }

        return songs;
    }

    private static int findIndex(String[] header, String... names) {

        for (int i = 0; i < header.length; i++) {

            String col = clean(header[i]).replace("\uFEFF", "").toLowerCase();
            for (String name : names) {
                if (col.equals(name)) return i;
            }
        }

        return -1;
    }

    private static String clean(String value) {
        return value == null ? "" : value.trim();
    }
}
