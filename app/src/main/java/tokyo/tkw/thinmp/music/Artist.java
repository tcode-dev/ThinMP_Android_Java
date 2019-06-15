package tokyo.tkw.thinmp.music;

import java.util.ArrayList;

/**
 * Artist
 */
public class Artist extends Music {
    public static final String ARTIST_ID = "artistId";
    private String id;
    private String name;
    private ArrayList<String> albumIdList = new ArrayList<>();

    public Artist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getThumbnailIdList() {
        return albumIdList;
    }
}
