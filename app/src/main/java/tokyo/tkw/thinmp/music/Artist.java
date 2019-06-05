package tokyo.tkw.thinmp.music;

import android.graphics.Bitmap;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import tokyo.tkw.thinmp.provider.ThumbnailProvider;

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

    public Bitmap getThumbnail() {
        List<String> idList =
                Stream.of(albumIdList).map(id -> MusicList.getAlbum(id).getThumbnailId()).distinct().collect(Collectors.toList());
        ThumbnailProvider thumbnailProvider = new ThumbnailProvider();

        return thumbnailProvider.getThumbnail(idList);
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
