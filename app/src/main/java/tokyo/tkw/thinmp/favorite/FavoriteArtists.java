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
    private RealmResults<FavoriteArtistRealm> favoriteRealmResults;
    private List<FavoriteArtistRealm> favoriteList;
    private List<String> artistIdList;
    private List<Artist> artistList;
    private Map<String, Artist> artistMap;
    private List<Artist> sortedArtistList;

    private FavoriteArtists(Context context) {
        this.realm = Realm.getDefaultInstance();
        this.provider = new ArtistContentProvider(context);
    }

    public static FavoriteArtists createInstance(Context context) {
        return new FavoriteArtists(context);
    }

    public List<Artist> getSortedArtistList() {
        favoriteRealmResults = getFavoriteRealmResults();
        favoriteList = getFavoriteList();
        artistIdList = getArtistIdList();
        artistList = getArtistList();
        artistMap = getArtistMap();
        sortedArtistList = sortArtistList();

        validation();

        return sortedArtistList;
    }

    private RealmResults<FavoriteArtistRealm> getFavoriteRealmResults() {
        return realm.where(FavoriteArtistRealm.class).findAll().sort(FavoriteArtistRealm.ID);
    }

    private List<FavoriteArtistRealm> getFavoriteList() {
        return Stream.of(favoriteRealmResults).toList();
    }

    private List<String> getArtistIdList() {
        return Stream.of(favoriteList).map(FavoriteArtistRealm::getArtistId).collect(Collectors.toList());
    }

    private List<Artist> getArtistList() {
        return provider.findById(artistIdList);
    }

    private Map<String, Artist> getArtistMap() {
        return Stream.of(artistList).collect(Collectors.toMap(Artist::getId, artist -> artist));
    }

    private List<Artist> sortArtistList() {
        return Stream.of(artistIdList).filter(artistMap::containsKey).map(artistMap::get).toList();
    }

    private void validation() {
        if (isDeleted()) {
            remove();
        }
    }

    private boolean isDeleted() {
        return artistIdList.size() != sortedArtistList.size();
    }

    private void remove() {
        List<String> removeList = Stream.of(artistIdList)
                .filter(id -> !artistMap.containsKey(id))
                .map(id -> id)
                .collect(Collectors.toList());

        FavoriteArtistRegister register = FavoriteArtistRegister.createInstance();
        register.delete(removeList);
    }
}
