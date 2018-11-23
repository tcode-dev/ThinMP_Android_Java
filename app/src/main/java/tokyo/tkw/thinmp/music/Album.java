package tokyo.tkw.thinmp.music;

import java.util.ArrayList;

/**
 * Album
 */
public class Album {
    private String id;
    private String name;
    private String artistId;
    private String artistName;
    private String thumbnailId;
    private ArrayList<String> trackIdList = new ArrayList<String>();

    public Album(String id, String name, String artistId, String artistName, String thumbnailId) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
        this.artistName = artistName;
        this.thumbnailId = thumbnailId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getThumbnailId() {
        return thumbnailId;
    }

    public ArrayList<String> getTrackIdList() {
        return trackIdList;
    }

    public void addTrackId(String id) {
        trackIdList.add(id);
    }
}
