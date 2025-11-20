package com.Revature;
import java.util.Date;

public class Playlist {
    //fields
    private int playlist_id;
    private String title;
    private boolean favorite;

    //constructors
    public Playlist(){}

    //to set id and title
    public Playlist(int playlist_id, String title, boolean favorite){
        this.playlist_id = playlist_id;
        this.title = title;
        this.favorite = favorite;
    }

    //to only set title
    public Playlist(String title){
        this.title = title;
    }

    //methods
    public int getId(){
        return playlist_id;
    }
    public void setId(int id){
        this.playlist_id = playlist_id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public boolean getFavorite(){return favorite;}
    public void setFavorite(boolean favorite){this.favorite = favorite;}






}
