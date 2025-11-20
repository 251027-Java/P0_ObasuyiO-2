package com.Revature.Service;

import com.Revature.Music;
import com.Revature.Playlist;
import com.Revature.Repository.MusicRepo;
import com.Revature.Repository.PlaylistRepo;

import java.util.List;

public class PlaylistService {
    //fields
    private PlaylistRepo playlistRepo;
    private MusicRepo musicRepo;

    //constructor
    public PlaylistService(PlaylistRepo playlistRepo, MusicRepo musicRepo) {
        this.playlistRepo = playlistRepo;
        this.musicRepo = musicRepo;
    }

    //methods
    //service method to create a new playlist
    public void createPlaylist(String title, boolean favorite) {
        Playlist playlist = new Playlist();
        playlist.setTitle(title);
        playlist.setFavorite(favorite);
        playlistRepo.newPlaylist(playlist);
        IO.println("Playlist created: " + title);
    }

    //service method to add song to a playlist by song_id and playlist_id
    public void addSongToPlaylist(int playlist_id, int song_id) {
        playlistRepo.addSong(playlist_id, song_id);
        IO.println("Song ID " + song_id + " added to playlist ID " + playlist_id);
    }

    //service method to remove song from a playlist by song_id and playlist_id
    public void removeSongFromPlaylist(int playlist_id, int song_id) {
        playlistRepo.removeSong(playlist_id, song_id);
        IO.println("Song ID " + song_id + " removed from playlist ID " + playlist_id);
    }

    //service method to show all playlists created
    public void showAllPlaylists() {
        List<Playlist> playlists = playlistRepo.showAllPlaylists();
        IO.println("All Playlists:");
        for (Playlist p : playlists) {
            IO.println("ID: " + p.getId() + " | Title: " + p.getTitle() + " | Favorite: " + p.getFavorite());
        }
    }

    //service method to show all songs in a specific playlist
    public void showPlaylistSongs(int playlistId) {
        List<Music> songs = playlistRepo.getPlaylistSongs(playlistId);
        System.out.println("Songs in playlist ID " + playlistId + ":");
        for (Music song : songs) {
            System.out.println("ID: " + song.getId() + " | Title: " + song.getTitle() +
                    " | Artist ID: " + song.getArtist_id() +
                    " | Album ID: " + song.getAlbum_id());
        }
    }

    //service method to search for playlist by title
    public void searchPlaylists(String query) {
        String results = playlistRepo.searchByTitleOrArtist(query);
        IO.println(results);
    }

    //service method to delete playlist by playlist id
    public void deletePlaylist(int playlist_id) {
        playlistRepo.deleteById(playlist_id);
        IO.println("Playlist ID " + playlist_id + " deleted.");
    }

}
