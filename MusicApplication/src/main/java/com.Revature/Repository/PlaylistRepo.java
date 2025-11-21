package com.Revature.Repository;
import com.Revature.Music;
import com.Revature.Playlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistRepo implements ImusicRepo {
    //fields
    private Connection connection;

    //constructor for connection
    public PlaylistRepo(Connection connection) {
        this.connection = connection;
    }
    public PlaylistRepo() {
        try {
            // Make sure these match your DB
            String url = "jdbc:postgresql://localhost:5432/musicplayerdb";
            String user = "postgres";
            String pass = "mysecretpassword";
            this.connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //methods
    //method to create new playlist into Playlists table using CRUD
    public void newPlaylist(Playlist playlist) {
        String sql = "INSERT INTO Playlists (title) VALUES (?);";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, playlist.getTitle());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //method to find a playlist by title using sql query
    public void findByTitle(String title) throws SQLException {
        String sql = "SELECT playlist_id, title, favorite FROM musicPlayer.Playlists WHERE title = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                Playlist np = new Playlist();
                np.setId(result.getInt("playlist_id"));
                np.setTitle(result.getString("title"));
                np.setFavorite(result.getBoolean("favorite"));
            }
        } catch (SQLException e) {
            System.err.println("Error finding playlist titled: " + e.getMessage());
        }
    }

    //method to add a song into a specific playlist using CRUD
    public void addSong(int playlist_id, int song_id) {
        String sql = "INSERT INTO PlaylistSongs (playlist_id, song_id) VALUES (?, ?) ON CONFLICT DO NOTHING;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlist_id);
            stmt.setInt(2, song_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding song: " + e.getMessage());
        }
    }

   //method to remove a song from a specific playlist using CRUD
    public void removeSong(int playlist_id, int song_id) {
        String sql = "DELETE FROM PlaylistSongs WHERE playlist_id = ? AND song_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlist_id);
            stmt.setInt(2, song_id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error removing song: " + e.getMessage());
        }
    }

    //method to retrieve all songs from a specific playlist identified by playlist id using sql query
    public List<Music> getPlaylistSongs(int playlist_id) {
        String sql = "SELECT Songs.song_id, Songs.title, Songs.artist, Songs.album " +
                "FROM Songs JOIN PlaylistSongs ON Songs.song_id = PlaylistSongs.song_id " +
                "WHERE PlaylistSongs.playlist_id = ? ORDER BY Songs.title;";
        List<Music> songs = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, playlist_id);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Music song = new Music();
                song.setId(result.getInt("song_id"));
                song.setTitle(result.getString("title"));
                song.setArtist(result.getString("artist"));
                song.setAlbum(result.getString("album"));
                songs.add(song);
            }
        } catch (SQLException e) {
            System.err.println("Error finding playlist songs from: " + e.getMessage());
        }
        return songs;
    }

    //method to show all created playlist by user using sql query
    public List<Playlist> showAllPlaylists() {
        String sql = "SELECT playlist_id, title, favorite FROM Playlists ORDER BY playlist_id DESC";
        List<Playlist> playlists = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                Playlist newP = new Playlist();
                newP.setId(result.getInt("playlist_id"));
                newP.setTitle(result.getString("title"));
                newP.setFavorite(result.getBoolean("favorite"));
                playlists.add(newP);
            }
        } catch (SQLException e) {
            System.err.println("Error finding playlists from: " + e.getMessage());
        }
        return playlists;
    }

    //overidden findById method to select a playlist based on playlist_id
    @Override
    public int findById(int id) {
        String sql = "SELECT playlist_id FROM Playlists WHERE playlist_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                return result.getInt("playlist_id");
            }
        } catch (SQLException e) {
            System.err.println("Error finding playlist by id: " + e.getMessage());
        }
        return 0;
    }

    //overidden deleteById method to delete a playlist based on playlist_id
    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM Playlists WHERE playlist_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting playlist by id: " + e.getMessage());
        }
    }

    //overidden method to find a song by a specific title that is in a specific playlist
    @Override
    public String searchByTitleOrArtist(String query) {
        String sql = "SELECT playlist_id, title FROM Playlists " +
                "WHERE title LIKE ? ORDER BY title ASC;";
        StringBuilder result = new StringBuilder();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            //creates search pattern for SQL like query to ignore characters before or after
            String like = "%" + query + "%";
            stmt.setString(1, like);
            ResultSet r = stmt.executeQuery();
            while (r.next()) {
                int id = r.getInt("playlist_id");
                String title = r.getString("title");
                result.append("playlist ID: ").append(id)
                        .append(", title: ").append(title).append("\n");
            }
        } catch (SQLException e) {
            System.err.println("Error searching playlists: " + e.getMessage());
        }
        if (result.isEmpty()) {
            return "No playlist/song found matching: " + query;
        }
        return result.toString();
    }
}


