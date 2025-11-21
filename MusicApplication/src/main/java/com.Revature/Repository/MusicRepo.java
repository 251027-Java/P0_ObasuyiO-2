package com.Revature.Repository;
import com.Revature.Music;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class MusicRepo implements ImusicRepo {
    //included all sql stuff for music/classes, create tables for 3NF and many-to-many, and establish database connection
    //fields - connection string/database username and password
    private static final String connectionURL = "jdbc:postgresql://localhost:5432/musicplayerdb";
    private static final String connectionUser = "postgres";
    private static final String connectionPass = "mysecretpassword";
    private Connection connection;

    //SQL repository constructor to create tables/database
    public MusicRepo(){
        try{
            connection = DriverManager.getConnection(connectionURL, connectionUser, connectionPass);
            tableCreator();
            IO.println("Successfully created tables for database!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //method to create all the tables (in one method); used instead/in addition to creating it in a SQL file (will have musicDB.sql)
    private void tableCreator() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create schema
            stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS musicPlayer");

            // TABLE: Artist
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS musicPlayer.Artist (" +
                    "artist_id SERIAL PRIMARY KEY, " +
                    "artist_name VARCHAR(70) NOT NULL" +
                    ")"
            );
            // TABLE: Album
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS musicPlayer.Album (" +
                    "album_id SERIAL PRIMARY KEY, " +
                    "album_name VARCHAR(70) NOT NULL, " +
                    "artist_id INT, " +
                    "FOREIGN KEY (artist_id) REFERENCES musicPlayer.Artist(artist_id)" +
                    ")"
            );

            // TABLE: Songs
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS musicPlayer.Songs (" +
                            "song_id SERIAL PRIMARY KEY, " +
                            "title VARCHAR(70) NOT NULL, " +
                            "artist_id INT, " +
                            "album_id INT, " +
                            "FOREIGN KEY (artist_id) REFERENCES musicPlayer.Artist(artist_id), " +
                            "FOREIGN KEY (album_id) REFERENCES musicPlayer.Album(album_id)" +
                            ")"
            );
            // TABLE: Playlists
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS musicPlayer.Playlists (" +
                            "playlist_id SERIAL PRIMARY KEY, " +
                            "title VARCHAR(70) NOT NULL, " +
                            "favorite BOOLEAN" +
                            ")"
            );

            // TABLE: PlaylistSongs (many-to-many)
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS musicPlayer.PlaylistSongs (" +
                            "playlist_id INT, " +
                            "song_id INT, " +
                            "PRIMARY KEY (playlist_id, song_id), " +
                            "FOREIGN KEY (playlist_id) REFERENCES musicPlayer.Playlists(playlist_id), " +
                            "FOREIGN KEY (song_id) REFERENCES musicPlayer.Songs(song_id)" +
                            ")"
            );
            System.out.println("Tables created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //constructor to create new song in Music(songs) table
    public void newSong(Music Music){
        String sql = "INSERT INTO Songs (title, artist_id, album) VALUES (?, ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, Music.getTitle());
            stmt.setInt(2, Music.getArtist_id());
            stmt.setString(3, Music.getAlbum());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //method to view all songs in Music(songs) table
    public List<Music> getAllSongs() {
    String sql = "SELECT song_id, title, artist_id, album " +
                 "FROM musicPlayer.Songs " +
                 "ORDER BY title;";
    List<Music> songs = new ArrayList<>();
    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        ResultSet result = stmt.executeQuery();
        while (result.next()) {
            Music song = new Music();
            song.setId(result.getInt("song_id"));
            song.setTitle(result.getString("title"));
            song.setArtist_id(result.getInt("artist_id"));
            song.setAlbum(result.getString("album"));  // or album_id, depending on schema
            songs.add(song);
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving all songs: " + e.getMessage());
    }
    return songs;
}

    //overidden findById method to select a song based on song_id
    @Override
    public int findById(int id) {
        String sql = "SELECT song_id FROM musicPlayer.Songs WHERE song_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()){
                return result.getInt("song_id");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return 0; //song not found
    }

    //overidden deleteById method to delete a song based on song_id
    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM musicPlayer.Songs WHERE song_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id);
            int rowGone = stmt.executeUpdate();
            if (rowGone > 0){
                IO.println("Song deleted successfully.");
            } else {
                IO.println("No song with that ID was found");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    //overidden search method to find song by song title and/or song artist
    @Override
    public String searchByTitleOrArtist(String query) {
        String sql = "SELECT Songs.song_id, Songs.title, Artist.artist_name " +
                "FROM musicPlayer.Songs " +
                "LEFT JOIN musicPlayer.Artist ON Songs.artist_id = Artist.artist_id " +
                "WHERE LOWER(Songs.title) LIKE LOWER(?) " +
                "OR LOWER(Artist.artist_name) LIKE LOWER(?);";
        StringBuilder songFound = new StringBuilder();
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            //creates search pattern for SQL like query to ignore characters before or after
            String charsIncluded = "%" + query + "%";
            stmt.setString(1, charsIncluded);
            stmt.setString(2, charsIncluded);
            ResultSet result = stmt.executeQuery();
            while (result.next()){
                songFound.append("ID: ").append(result.getInt("song_id"))
                        .append(" | Title: ").append(result.getString("title"))
                        .append(" | Artist: ").append(result.getString("artist_name"))
                        .append("\n");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        if (songFound.length() == 0){
            return "No songs found for query: " + query;
        }
        return songFound.toString();
    }
}
