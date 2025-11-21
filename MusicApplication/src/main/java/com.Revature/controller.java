package com.Revature;

import java.util.Scanner;

import com.Revature.Service.MusicService;
import com.Revature.Service.PlaylistService;

public class controller {
    private final MusicService musicService;
    private final PlaylistService playlistService;
    private final Scanner scanner = new Scanner(System.in);

    public controller(MusicService musicService, PlaylistService playlistService) {
        this.musicService = musicService;
        this.playlistService = playlistService;
    }

    public void run() {
        System.out.println("Welcome to Osi's project 0!");
        boolean running = true;

        while (running) {
            System.out.println("Let's get started! what do u want to do first?)");
            System.out.println("1. view all songs");
            System.out.println("2. add songs to database");
            System.out.println("3. remove songs from database");
            System.out.println("4. look for a song");
            System.out.println("5. play a song");
            System.out.println("6. view all playlists");
            System.out.println("7. create a new playlist");
            System.out.println("8. show songs in a playlist");
            System.out.println("9. Add song to playlist");
            System.out.println("10. remove song from a playlist");
            System.out.println("11. find a playlist");
            System.out.println("12. delete a playlist");
            System.out.println("13. Look for a song");
            System.out.println("14. Exit");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": // view all songs
                    // repo search with empty query returns all songs
                    musicService.viewAllSongs();
                    break;
                case "2": // add song to database
                    System.out.print("Enter song title: ");
                    String songTitle = scanner.nextLine();
                    System.out.print("Enter artist id (number): ");
                    int artistId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter album id (number): ");
                    int albumId = Integer.parseInt(scanner.nextLine());
                    Music newSong = new Music();
                    newSong.setTitle(songTitle);
                    newSong.setArtist_id(artistId);
                    newSong.setAlbum_id(albumId);
                    musicService.addSong(newSong);
                    break;
                case "3": // remove song from database
                    System.out.print("Enter song ID to delete: ");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    musicService.deleteSong(deleteId);
                    break;
                case "4": // look for a song
                    System.out.print("Enter search query (title or artist): ");
                    String query = scanner.nextLine();
                    musicService.searchSongs(query);
                    break;
                case "5": // play a song
                    System.out.print("Enter song ID to play: ");
                    int playId = Integer.parseInt(scanner.nextLine());
                    musicService.playSong(playId);
                    break;
                case "6": // view all playlists
                    playlistService.showAllPlaylists();
                    break;
                case "7": // create a new playlist
                    System.out.print("Enter playlist title: ");
                    String title = scanner.nextLine();
                    System.out.print("Mark as favorite? (y/n): ");
                    String fav = scanner.nextLine().trim().toLowerCase();
                    boolean favorite = fav.equals("y") || fav.equals("yes");
                    playlistService.createPlaylist(title, favorite);
                    break;
                case "8": // show songs in a playlist
                    System.out.print("Enter playlist ID: ");
                    int playlistId = Integer.parseInt(scanner.nextLine());
                    playlistService.showPlaylistSongs(playlistId);
                    break;
                case "9": // add song to playlist
                    System.out.print("Enter playlist ID: ");
                    int addPlaylistId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter song ID: ");
                    int addSongId = Integer.parseInt(scanner.nextLine());
                    playlistService.addSongToPlaylist(addPlaylistId, addSongId);
                    break;
                case "10": // remove song from a playlist
                    System.out.print("Enter playlist ID: ");
                    int remPlaylistId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter song ID: ");
                    int remSongId = Integer.parseInt(scanner.nextLine());
                    playlistService.removeSongFromPlaylist(remPlaylistId, remSongId);
                    break;
                case "11": // find a playlist
                    System.out.print("Enter playlist search query: ");
                    String pQuery = scanner.nextLine();
                    playlistService.searchPlaylists(pQuery);
                    break;
                case "12": // delete a playlist
                    System.out.print("Enter playlist ID to delete: ");
                    int delPlaylistId = Integer.parseInt(scanner.nextLine());
                    playlistService.deletePlaylist(delPlaylistId);
                    break;
                case "13": // look for a song (duplicate)
                    System.out.print("Enter song search query: ");
                    String sQuery = scanner.nextLine();
                    musicService.searchSongs(sQuery);
                    break;
                case "14": // Exit
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("Thank you for using the Music Application!");
    }
    

    
    
}
