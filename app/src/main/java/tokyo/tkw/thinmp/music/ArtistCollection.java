package tokyo.tkw.thinmp.music;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

import tokyo.tkw.thinmp.provider.AllArtistsContentProvider;
import tokyo.tkw.thinmp.provider.ArtistAlbumArtContentProvider;

public class ArtistCollection {
    private Context mContext;
    private ArrayList<Artist> mArtistList;

    public ArtistCollection(Context context, ArrayList<Artist> artistList) {
        mContext = context;
        mArtistList = artistList;
    }

    public ArrayList<Artist> getList() {
        return mArtistList;
    }

    public static ArtistCollection createAllArtistCollectionInstance(Context context) {
        AllArtistsContentProvider provider = new AllArtistsContentProvider(context);
        return new ArtistCollection(context, provider.getList());
    }

    public ArrayList<ArtistAlbumArt> getArtistAlbumArtList() {
        ArtistAlbumArtContentProvider artistAlbumArtContentProvider =
                new ArtistAlbumArtContentProvider(mContext);
        return artistAlbumArtContentProvider.getList();
    }

    public Map<String, String> getArtistAlbumArtMap() {
        return getArtistAlbumArtMap(getArtistAlbumArtList());
    }

    public Map<String, String> getArtistAlbumArtMap(ArrayList<ArtistAlbumArt> artistAlbumArtList) {
        return Stream.of(artistAlbumArtList)
                .distinctBy(artistAlbumArt -> artistAlbumArt.artistId)
                .collect(Collectors.toMap(
                        artistAlbumArt -> artistAlbumArt.artistId,
                        artistAlbumArt -> artistAlbumArt.albumArtId
                ));
    }
}
