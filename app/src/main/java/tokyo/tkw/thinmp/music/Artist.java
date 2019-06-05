package tokyo.tkw.thinmp.music;

import java.util.ArrayList;

/**
 * Artist
 */
public class Artist {
    private String id;
    private String name;
    private ArrayList<String> albumIdList = new ArrayList<>();
    private ArrayList<String> trackIdList = new ArrayList<>();

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
