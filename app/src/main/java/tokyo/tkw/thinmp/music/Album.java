package tokyo.tkw.thinmp.music;

import java.util.ArrayList;

/**
 * アルバム情報
 */
public class Album extends Music {
    private String id;
    private String name;
    private String artistName;
    private String thumbnailId;
    private ArrayList<String> trackIdList = new ArrayList<>();

    public Album(String id, String name, String artistName, String thumbnailId) {
        this.id = id;
        this.name = name;
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
