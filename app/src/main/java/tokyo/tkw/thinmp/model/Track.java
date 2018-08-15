package tokyo.tkw.thinmp.model;

import android.net.Uri;

/**
 * Created by tk on 2018/03/22.
 */

public class Track {
    private String id;
    private String title;
    private Uri uri;
    private String artistId;
    private String artistName;
    private String albumId;
    private String albumName;
    private String thumbnailId;

    public Track(String id, String title, Uri uri, String artistId, String artistName, String albumId, String albumName, String thumbnailId) {
        this.id = id;
        this.title = title;
        this.uri = uri;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumName = albumName;
        this.thumbnailId = thumbnailId;
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

    public String getAlbumId() {return albumId;}

    public String getAlbumName() {return albumName;}

    public String getThumbnailId() {
        return thumbnailId;
    }


}
