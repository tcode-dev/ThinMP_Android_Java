package tokyo.tkw.thinmp.artist;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.provider.TrackContentProvider;

class ArtistTrack {
    private String artistId;
    private TrackContentProvider trackContentProvider;

    private ArtistTrack(Context context, String artistId) {
        this.artistId = artistId;
        this.trackContentProvider = new TrackContentProvider(context);
    }

    public static ArtistTrack createInstance(Context context, String artistId) {
        return new ArtistTrack(context, artistId);
    }

    public List<Track> getTrackList() {
        return trackContentProvider.findByArtist(artistId);
    }
}
