package tokyo.tkw.thinmp.music;

import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.track.Track;

public abstract class Music {
    public static final String ID = "Id";
    public static final String TYPE = "type";
    public static final int TYPE_TRACK = 1;
    public static final int TYPE_ALBUM = 2;

    protected String id;
    protected String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract List<Track> getTrackList();

    public List<String> getIdList() {
        return Stream.of(getTrackList()).map(Music::getId).toList();
    }
}
