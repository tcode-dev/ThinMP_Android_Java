package tokyo.tkw.thinmp.favorite;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;

public class FavoriteArtists {
    private Realm realm;
    private ArtistContentProvider provider;

    public FavoriteArtists(Context context) {
        this.realm = Realm.getDefaultInstance();
        this.provider = new ArtistContentProvider(context);
    }

    public List<Artist> getArtistList() {
        RealmResults<FavoriteArtistRealm> realmResults =
                realm.where(FavoriteArtistRealm.class).findAll().sort(FavoriteArtistRealm.ID);
        List<FavoriteArtistRealm> favoriteArtistRealmList = Stream.of(realmResults).toList();
        List<String> artistIdList = Stream.of(favoriteArtistRealmList)
                .map(FavoriteArtistRealm::getArtistId).collect(Collectors.toList());
        List<Artist> artistList = provider.findById(artistIdList);
        Map<String, Artist> artistMap = Stream.of(artistList)
                .collect(Collectors.toMap(Artist::getId, artist -> artist));

        return Stream.of(artistIdList).map(artistMap::get).toList();
    }
}
