package com.Revature;   
public class Music {
    //fields for music class (song info)
    private int song_id;
    private String title;
    private String artist;
    private String album;
    private boolean saved;
    private int artist_id;

    //constructors for a music object (a song with info essentially)
    public Music(){}

    //constructor for a song
    public Music(int song_id, String title, String artist, String album)
    {
        this.song_id = song_id;
        this.title = title;
        this. artist = artist;
        this.album = album;
    }

    //getters and setters
    public int getId(){
        return song_id;
    }
    public void setId(int id) {
        this.song_id = id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist(){
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum(){
        return album;
    }
    public void setAlbum(String album) {
        this.album = album;
    }

    public int getArtist_id(){
        return artist_id;
    }
    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }










}
