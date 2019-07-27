package tokyo.tkw.thinmp.track;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import com.annimon.stream.Optional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.provider.TrackContentProvider;
import tokyo.tkw.thinmp.util.TimeUtil;

import static android.net.Uri.parse;

/**
 * 曲
 */
public class Track extends Music implements Serializable {
    public static final String TRACK_ID = "trackId";

    private String artistId;
    private String artistName;
    private String albumId;
    private String albumName;
    private String albumArtId;
    private int duration;

    public Track(String id, String name, String artistId, String artistName, String albumId,
                 String albumName, String albumArtId, int duration) {
        this.id = id;
        this.name = name;
        this.artistId = artistId;
        this.artistName = artistName;
        this.albumId = albumId;
        this.albumName = albumName;
        this.albumArtId = albumArtId;
        this.duration = duration;
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

    public List<Track> getTrackList() {
        List<Track> trackList = new ArrayList<>();
        trackList.add(this);

        return trackList;
    }

    public static Optional<Track> createInstance(Context context, String id) {
        TrackContentProvider provider = new TrackContentProvider(context);

        return provider.findById(id);
    }
}
