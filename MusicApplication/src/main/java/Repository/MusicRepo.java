package Repository;
import org.example.Music;


public class MusicRepo implements ImusicRepo {
    //include all sql stuff for music and playlist classes while also establishing database connection (look at H2Repository)
    //fields- connection string

    //constructor to create new song
    public Music newSong(Music music){
        String sql = "INSERT INTO songs (title, artist, album, saved) VALUES (?, ?, ?, ?) RETURNING  song_id;";

    }
}
