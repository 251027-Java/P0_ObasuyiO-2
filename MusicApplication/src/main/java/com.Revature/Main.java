package com.Revature;

import com.Revature.Repository.MusicRepo;
import com.Revature.Repository.PlaylistRepo;
import com.Revature.Service.MusicService;
import com.Revature.Service.PlaylistService;

public class Main {
    public static void main(String[] args) {
        // repositories (handle DB connection inside)
        MusicRepo musicRepo = new MusicRepo();
        //PlaylistRepo playlistRepo = new PlaylistRepo();

        // services
        //MusicService musicService = new MusicService(musicRepo, playlistRepo);
        //PlaylistService playlistService = new PlaylistService(playlistRepo, musicRepo);

        // controller and run the application
        //controller appController = new controller(musicService, playlistService);
        //appController.run();
    }
}
