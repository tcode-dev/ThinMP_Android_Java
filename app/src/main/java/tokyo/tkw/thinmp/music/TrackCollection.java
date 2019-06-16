package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.ArrayList;

import tokyo.tkw.thinmp.provider.AlbumTracksContentProvider;
import tokyo.tkw.thinmp.provider.AllTracksContentProvider;
import tokyo.tkw.thinmp.provider.ArtistTracksContentProvider;

public class TrackCollection {
    private ArrayList<Track> mTrackList;

    private TrackCollection(ArrayList<Track> trackList) {
        mTrackList = trackList;
    }

    public static TrackCollection createAllTrackCollectionInstance(Context context) {
        AllTracksContentProvider provider = new AllTracksContentProvider(context);
        return new TrackCollection(provider.getList());
    }

    public static TrackCollection createArtistTrackCollectionInstance(Context context, String artistId) {
        ArtistTracksContentProvider provider = new ArtistTracksContentProvider(context, artistId);
        return new TrackCollection(provider.getList());
    }

    public static TrackCollection createAlbumTrackCollectionInstance(Context context, String albumId) {
        AlbumTracksContentProvider provider = new AlbumTracksContentProvider(context, albumId);
        return new TrackCollection(provider.getList());
    }

    public ArrayList<Track> getList() {
        return mTrackList;
    }
}
