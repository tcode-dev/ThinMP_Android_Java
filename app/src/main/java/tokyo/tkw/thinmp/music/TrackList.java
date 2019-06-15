package tokyo.tkw.thinmp.music;

import android.content.Context;

import java.util.ArrayList;

import tokyo.tkw.thinmp.provider.AlbumTracksContentProvider;
import tokyo.tkw.thinmp.provider.AllTracksContentProvider;
import tokyo.tkw.thinmp.provider.ArtistTracksContentProvider;

public class TrackList {
    private Context mContext;

    public TrackList(Context context) {
        mContext = context;
    }

    public ArrayList<Track> getAllTrackList() {
        AllTracksContentProvider provider = new AllTracksContentProvider(mContext);

        return provider.getList();
    }

    public ArrayList<Track> getArtistTrackList(String artistId) {

        ArtistTracksContentProvider provider = new ArtistTracksContentProvider(mContext, artistId);

        return provider.getList();
    }

    public ArrayList<Track> getAlbumTrackList(String albumId) {
        AlbumTracksContentProvider provider = new AlbumTracksContentProvider(mContext, albumId);

        return provider.getList();
    }
}
