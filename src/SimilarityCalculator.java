public final class SimilarityCalculator {
    private SimilarityCalculator() {
    }

    public static double between(Song a, Song b) {
        long max = Math.max(a.getStreams(), b.getStreams());
        double streamSimilarity = max == 0
                ? 1.0
                : 1.0 - (Math.abs(a.getStreams() - b.getStreams()) / (double) max);
        double artistSimilarity = sameKnownValue(a.getArtist(), b.getArtist()) ? 1.0 : 0.0;
        double genreSimilarity = sameKnownValue(a.getGenre(), b.getGenre()) ? 1.0 : 0.0;
        return 0.5 * streamSimilarity + 0.3 * artistSimilarity + 0.2 * genreSimilarity;
    }

    private static boolean sameKnownValue(String first, String second) {
        return first != null && second != null
                && !first.isBlank() && !second.isBlank()
                && !first.equalsIgnoreCase("Unknown")
                && first.equalsIgnoreCase(second);
    }
}
