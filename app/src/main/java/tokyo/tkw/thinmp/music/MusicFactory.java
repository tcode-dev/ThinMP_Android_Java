package tokyo.tkw.thinmp.music;

import android.content.Context;

import com.annimon.stream.Optional;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.track.Track;

public class MusicFactory {
    public static Optional<Music> createInstance(Context context, int type, String id) {
        switch (type) {
            case Music.TYPE_TRACK:
                Optional<Track> track = Track.createInstance(context, id);
                return track.isEmpty() ? Optional.empty() : Optional.ofNullable((Music) track.get());

            case Music.TYPE_ALBUM:
                Optional<Album> album = Album.createInstance(context, id);
                return album.isEmpty() ? Optional.empty() : Optional.ofNullable((Music) album.get());

            default:
                return Optional.empty();
        }
    }
}
