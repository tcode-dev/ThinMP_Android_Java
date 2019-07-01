package tokyo.tkw.thinmp.music;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

import tokyo.tkw.thinmp.provider.AlbumArtContentProvider;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;

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
        ArtistContentProvider provider = new ArtistContentProvider(context);

        return new ArtistCollection(context, provider.findAll());
    }

    public ArrayList<Album> getAllArtistAlbumArtList() {
        AlbumArtContentProvider provider = new AlbumArtContentProvider(mContext);

        return provider.findAll();
    }

    public Map<String, String> getAllArtistAlbumArtMap() {
        return toArtistAlbumArtMap(getAllArtistAlbumArtList());
    }

    public Map<String, String> toArtistAlbumArtMap(ArrayList<Album> artistAlbumList) {
        return Stream.of(artistAlbumList)
                .distinctBy(Album::getArtistId)
                .collect(Collectors.toMap(Album::getArtistId, Album::getAlbumArtId));
    }
}
