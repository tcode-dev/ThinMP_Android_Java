package tokyo.tkw.thinmp.music;

import android.net.Uri;

import java.io.Serializable;

import tokyo.tkw.thinmp.util.TimeUtil;

/**
 * 曲
 */
public class Track extends Music implements Serializable {
    private String id;
    private String title;
    private Uri uri;
    private String artistId;
    private String artistName;
    private String albumId;
    private String albumName;
    private String thumbnailId;
    private int duration;

    public Track(String id, String title, Uri uri, String artistId, String artistName, String albumId, String albumName, int duration) {
        this.id = id;
        this.title = title;
        this.uri = uri;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumName = albumName;
        this.thumbnailId = albumId;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Uri getUri() {
        return uri;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getThumbnailId() {
        return thumbnailId;
    }

    public int getDurationSecond() {
        return TimeUtil.millisecondToSecond(duration);
    }

    public String getDurationTime() {
        return TimeUtil.secondToTime(getDurationSecond());
    }
}
