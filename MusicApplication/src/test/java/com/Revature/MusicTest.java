package com.Revature;

import com.Revature.Repository.MusicRepo;
import com.Revature.Repository.PlaylistRepo;
import com.Revature.Service.MusicService;
import com.Revature.Service.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MusicTest {

    @Mock
    private MusicRepo musicRepo;

    @Mock
    private PlaylistRepo playlistRepo;

    @InjectMocks
    private MusicService musicService;

    @InjectMocks
    private PlaylistService playlistService;

    @BeforeEach
    void setup() {
        // Mockito will inject mocks into the services because of @InjectMocks
    }

    @Test
    void addSong_callsRepoNewSong() {
        Music m = new Music();
        m.setTitle("Title");
        m.setArtist_id(1);
        m.setAlbum_id(2);

        musicService.addSong(m);

        verify(musicRepo, times(1)).newSong(m);
    }

    @Test
    void deleteSong_callsRepoDeleteById() {
        int id = 42;

        musicService.deleteSong(id);

        verify(musicRepo, times(1)).deleteById(id);
    }

    @Test
    void playSong_callsSearchByTitleOrArtist_withStringId() {
        int id = 7;
        when(musicRepo.searchByTitleOrArtist(String.valueOf(id))).thenReturn("Found Song");

        musicService.playSong(id);

        verify(musicRepo, times(1)).searchByTitleOrArtist(String.valueOf(id));
    }

    @Test
    void searchSongs_callsRepoWithQuery() {
        String q = "beatles";
        when(musicRepo.searchByTitleOrArtist(q)).thenReturn("results");

        musicService.searchSongs(q);

        verify(musicRepo, times(1)).searchByTitleOrArtist(q);
    }

    @Test
    void viewAllSongs_callsRepoGetAllSongs() {
        // The service method `viewAllSongs` prints result; we verify repo interaction
        when(musicRepo.getAllSongs()).thenReturn(Collections.emptyList());

        musicService.viewAllSongs();

        verify(musicRepo, times(1)).getAllSongs();
    }

    @Test
    void createPlaylist_callsRepoNewPlaylist_withCorrectFields() {
        String title = "My List";
        boolean favorite = true;

        playlistService.createPlaylist(title, favorite);

        ArgumentCaptor<Playlist> captor = ArgumentCaptor.forClass(Playlist.class);
        verify(playlistRepo, times(1)).newPlaylist(captor.capture());

        Playlist created = captor.getValue();
        assertEquals(title, created.getTitle());
        assertEquals(favorite, created.getFavorite());
    }

    @Test
    void addSongToPlaylist_callsRepoAddSong() {
        int pid = 3, sid = 11;

        playlistService.addSongToPlaylist(pid, sid);

        verify(playlistRepo, times(1)).addSong(pid, sid);
    }

    @Test
    void removeSongFromPlaylist_callsRepoRemoveSong() {
        int pid = 5, sid = 9;

        playlistService.removeSongFromPlaylist(pid, sid);

        verify(playlistRepo, times(1)).removeSong(pid, sid);
    }

    @Test
    void showAllPlaylists_callsRepoAndIterates() {
        Playlist p = new Playlist();
        p.setId(1);
        p.setTitle("a");
        p.setFavorite(false);

        when(playlistRepo.showAllPlaylists()).thenReturn(List.of(p));

        playlistService.showAllPlaylists();

        verify(playlistRepo, times(1)).showAllPlaylists();
    }

    @Test
    void showPlaylistSongs_callsRepoGetPlaylistSongs() {
        int pid = 2;
        when(playlistRepo.getPlaylistSongs(pid)).thenReturn(Collections.emptyList());

        playlistService.showPlaylistSongs(pid);

        verify(playlistRepo, times(1)).getPlaylistSongs(pid);
    }
}
