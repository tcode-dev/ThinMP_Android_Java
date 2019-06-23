package tokyo.tkw.thinmp.music;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.Serializable;

import tokyo.tkw.thinmp.fragment.PlaylistDialogFragment;
import tokyo.tkw.thinmp.provider.AlbumContentProvider;
import tokyo.tkw.thinmp.provider.TrackContentProvider;
import tokyo.tkw.thinmp.util.TimeUtil;

import static android.net.Uri.parse;

/**
 * 曲
 */
public class Track extends Music implements Serializable {
    public static final String TRACK_ID = "trackId";

    private String id;
    private String title;
    private String artistId;
    private String artistName;
    private String albumId;
    private String albumName;
    private String albumArtId;
    private int duration;

    public Track(String id, String title, String artistId, String artistName, String albumId, String albumName, int duration) {
        this.id = id;
        this.title = title;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumName = albumName;
        this.albumArtId = albumId;
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
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

    public String getAlbumArtId() {
        return albumArtId;
    }

    public int getDurationSecond() {
        return TimeUtil.millisecondToSecond(duration);
    }

    public String getDurationTime() {
        return TimeUtil.secondToTime(getDurationSecond());
    }

    public Uri getUri() {
        return parse(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI + "/" + id);
    }

    /**
     * 指定された曲を取得
     *
     * @param context
     * @param id
     * @return
     */
    public static Track createInstance(Context context, String id) {
        TrackContentProvider provider = new TrackContentProvider(context, id);

        return provider.get();
    }
}
