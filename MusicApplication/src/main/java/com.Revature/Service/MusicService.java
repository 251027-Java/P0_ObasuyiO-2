package com.Revature.Service;

import com.Revature.Music;
import com.Revature.Repository.MusicRepo;
import com.Revature.Repository.PlaylistRepo;

public class MusicService {
    //fields
    private MusicRepo musicRepo;
    private PlaylistRepo playlistRepo;

    //constructor to create MusicService
    public MusicService(MusicRepo musicRepo, PlaylistRepo playlistRepo) {
        this.musicRepo = musicRepo;
        this.playlistRepo = playlistRepo;
    }

    //methods
    //service method to add song to table/database
    public void addSong(Music music) {
        musicRepo.newSong(music);
    }

    //service method to delete song from table/database
    public void deleteSong(int song_id) {
        musicRepo.deleteById(song_id);
    }

    //service method to play song
    public void playSong(int song_id) {
        // Retrieve the song info
        String song = musicRepo.searchByTitleOrArtist(String.valueOf(song_id));
        IO.println("Now playing: " + song);
    }

    //service method to search for songs by title or artist and display it without playing
    public void searchSongs(String query) {
        String songFound = musicRepo.searchByTitleOrArtist(query);
        IO.println(songFound);
    }

    public void viewAllSongs() {
        String allSongs = musicRepo.getAllSongs();
        IO.println(allSongs);
    }

}
