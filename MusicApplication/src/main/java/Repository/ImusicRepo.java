package Repository;

public interface ImusicRepo {
    int findById(int id);
    void deleteById(int id);
    String searchByTitleOrArtist(String query);
}
