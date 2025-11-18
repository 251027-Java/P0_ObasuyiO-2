package com.Revature.Repository;
import com.Revature.Music;
import java.sql.*;


public class MusicRepo implements ImusicRepo {
    //next is playlist repo and musicService and database creation (SQL file)
    //after that is main method creation and test cases
    //included all sql stuff for music/classes, create tables for 3NF and many-to-many, and establish database connection
    //fields - connection string/database username and password
    private static final String connectionURL = "jdbc:connectionURL://localhost:5432/musicplayerdb";
    private static final String connectionUser = "ahaha";
    private static final String connectionPass = "ahaha";
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
    private void tableCreator() throws SQLException{
        try (Statement stmt = connection.createStatement()){
            stmt.executeUpdate("CREATE SCHEMA IF NOT EXISTS musicPlayer");

            //create table for music.java
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS musicPlayer.Music(songs) (+" +
                    "song_id INT PRIMARY KEY," +
                    "title VARCHAR (70) NOT NULL," +
                    "artist_id INT," +
                    "album INT," +
                    //3NF no repeating groups
                    "FOREIGN KEY (artist_id) REFERENCES musicPlayer.Artist(artist_id)," +
                    "FOREIGN KEY (album_id) REFERENCES musicPlayer.Album(album_id)");

            //create table for playlist.java
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS musicPlayer.Playlist (+" +
                    "playlist_id INT PRIMARY KEY," +
                    "title VARCHAR (70) NOT NULL," +
                    "favorite BOOLEAN");

            //created table for music artists (3NF)
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS musicPlayer.Artist (+" +
                    "artist_id INT PRIMARY KEY," +
                    "artist_name VARCHAR(70) NOT NULL");

            //created table for albums (3NF)
            stmt.executeUpdate("CREATE TABLE musicPlayer.Album (+" +
                    "album_id INT PRIMARY KEY," +
                    "album_name VARCHAR(70) NOT NULL," +
                    "artist_id INT," +
                    //3NF no repeating groups
                    "FOREIGN KEY (artist_id) REFERENCES musicPlayer.Artist(artist_id)");

            //playlistSongs table for many-to-many relationship
            stmt.executeUpdate("CREATE TABLE musicPlayer.PlaylistSongs (+" +
                    "playlist_id INT," +
                    "song_id INT," +
                    "PRIMARY KEY (playlist_id, song_id)," +
                    "FOREIGN KEY (playlist_id) REFERENCES musicPlayer.Playlist(playlist_id)," +
                    "FOREIGN KEY (song_id) REFERENCES musicPlayer.Music(song_id)");

            IO.println("Tables created successfully");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //constructor to create new song in Music(songs) table
    public void newSong(Music Music){
        String sql = "INSERT INTO Music(songs) (song_id, title, artist_id, album) VALUES (?, ?, ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, Music.getId());
            stmt.setString(2, Music.getTitle());
            stmt.setInt(3, Music.getArtist_id());
            stmt.setString(4, Music.getAlbum());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    //overidden findById method to select a song based on song_id
    @Override
    public int findById(int id) {
        String sql = "SELECT song_id FROM musicPlayer.Music(song) WHERE song_id = ?;";
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

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM musicPlayer.Music(song) WHERE song_id = ?;";
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

    @Override
    public String searchByTitleOrArtist(String query) {
        String sql = "SELECT m.song_id, m.title, a.artist_name " +
                "FROM musicPlayer.Music m " +
                "LEFT JOIN musicPlayer.Artist a ON m.artist_id = a.artist_id " +
                "WHERE LOWER(m.title) LIKE LOWER(?) " +
                "OR LOWER(a.artist_name) LIKE LOWER(?);";
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
