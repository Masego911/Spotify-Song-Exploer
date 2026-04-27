/*
 * SONG CLASS
 *
 * PURPOSE:
 * Represents a single song as a data object.
 *
 * KEY FIX:
 * streams must be LONG (not int) because values exceed 2 billion.
 */
public class Song {

    private int id;           // unique identifier for each song
    private String title;     // song title
    private String artist;    // artist name
    private long streams;     // FIX: long allows very large numbers (> 2 billion)
    private String genre;     // optional attribute

    // Constructor initializes all fields
    public Song(int id, String title, String artist, long streams, String genre) {
        this.id = id;               // assign ID
        this.title = title;         // assign title
        this.artist = artist;       // assign artist
        this.streams = streams;     // assign streams (long)
        this.genre = genre;         // assign genre
    }

    // Getter for ID
    public int getId() {
        return id;
    }

    // Getter for title
    public String getTitle() {
        return title;
    }

    // Getter for artist
    public String getArtist() {
        return artist;
    }

    // Getter for streams (long)
    public long getStreams() {
        return streams;
    }

    // Getter for genre
    public String getGenre() {
        return genre;
    }
}