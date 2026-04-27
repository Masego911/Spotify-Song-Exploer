/*
 * VERTEX
 *
 * PURPOSE:
 * Wraps a Song object for graph use.
 *
 * CRITICAL:
 * Defines equality so visited checks and maps work correctly.
 */
public class Vertex {

    private Song song; // underlying data

    public Vertex(Song song) {
        this.song = song;
    }

    public Song getSong() {
        return song;
    }

    // -----------------------------
    // FIX: equality based on song ID
    // -----------------------------
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Vertex other = (Vertex) obj;

        return this.song.getId() == other.song.getId();
    }

    @Override
    public int hashCode() {

        return Integer.hashCode(song.getId());
    }
}