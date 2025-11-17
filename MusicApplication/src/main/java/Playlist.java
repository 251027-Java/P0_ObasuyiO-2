import java.util.Date;

public class Playlist {
    //fields
    private int id;
    private String title;

    //constructors
    public Playlist(){}

    //to set id and title
    public Playlist(int id, String title){
        this.id = id;
        this.title = title;
    }

    //to only set title
    public Playlist(String title){
        this.title = title;
    }

    //methods
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }






}
