package com.Revature.Repository;
import com.Revature.Music;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
//MusicApplication/src/main/java/Music.java


public class MusicRepo implements ImusicRepo {
    //tables done complete methods now
    //include all sql stuff for music/classes, create tables for 3NF and many-to-many, and establish database connection
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

    //constructor to create new song
    public Music newSong(Music music){
        String sql = "INSERT INTO songs (title, artist, album, saved) VALUES (?, ?, ?, ?) RETURNING  song_id;";
    }

    @Override
    public int findById(int id) {
        return 0;
    }

    @Override
    public void deleteById(int id) {
    }

    @Override
    public String searchByTitleOrArtist(String query) {
        return "";
    }
}
