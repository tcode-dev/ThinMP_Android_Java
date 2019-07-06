package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.provider.TrackContentProvider;

public class TrackCollection {
    private List<Track> mTrackList;

    private TrackCollection(List<Track> trackList) {
        mTrackList = trackList;
    }

    public static TrackCollection createAllTrackCollectionInstance(Context context) {
        TrackContentProvider provider = new TrackContentProvider(context);

        return new TrackCollection(provider.findAll());
    }

    public static TrackCollection createArtistTrackCollectionInstance(Context context,
                                                                      String artistId) {
        TrackContentProvider provider = new TrackContentProvider(context);

        return new TrackCollection(provider.findByArtist(artistId));
    }

    public static TrackCollection createAlbumTrackCollectionInstance(Context context,
                                                                     String albumId) {
        TrackContentProvider provider = new TrackContentProvider(context);

        return new TrackCollection(provider.findByAlbum(albumId));
    }

    public List<Track> getList() {
        return mTrackList;
    }
}
