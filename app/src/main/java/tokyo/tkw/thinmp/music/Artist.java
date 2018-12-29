package tokyo.tkw.thinmp.music;

import java.util.ArrayList;

/**
 * Artist
 */
public class Artist {
    private String id;
    private String name;
    private String thumbnailId;
    private ArrayList<String> albumIdList = new ArrayList<>();
    private ArrayList<String> trackIdList = new ArrayList<>();

    public Artist(String id, String name, String thumbnailId) {
        this.id = id;
        this.name = name;
        this.thumbnailId = thumbnailId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailId() {
        return thumbnailId;
    }

    public ArrayList<String> getAlbumIdList() {
        return albumIdList;
    }

    public void addAlbumId(String id) {
        if (albumIdList.contains(id)) return;

        albumIdList.add(id);
    }

    public ArrayList<String> getTrackIdList() {
        return trackIdList;
    }

    public void addTrackId(String id) {
        trackIdList.add(id);
    }
}
