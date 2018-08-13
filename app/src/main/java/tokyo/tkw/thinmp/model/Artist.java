package tokyo.tkw.thinmp.model;

import java.util.ArrayList;

/**
 * Created by tk on 2018/03/22.
 */

public class Artist {
    private String id;
    private String name;
    private String thumbnailId;
    private ArrayList<String> albumIdList = new ArrayList<String>();
    private ArrayList<String> trackIdList = new ArrayList<String>();

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
