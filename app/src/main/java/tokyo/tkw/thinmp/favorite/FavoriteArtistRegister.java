package tokyo.tkw.thinmp.favorite;

import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;
import tokyo.tkw.thinmp.realm.RealmRegister;

public class FavoriteArtistRegister extends RealmRegister {
    public static FavoriteArtistRegister createInstance() {
        return new FavoriteArtistRegister();
    }

    public boolean exists(String artistId) {
        FavoriteArtistRegister favoriteRegister = FavoriteArtistRegister.createInstance();

        return favoriteRegister.getArtist(artistId) != null;
    }

    private FavoriteArtistRealm getArtist(String artistId) {
        return realm.where(FavoriteArtistRealm.class).equalTo(FavoriteArtistRealm.ARTIST_ID, artistId).findFirst();
    }

    public void add(String artistId) {
        beginTransaction();

        realm.createObject(FavoriteArtistRealm.class, nextId()).setArtistId(artistId);

        commitTransaction();
    }

    public void remove(String artistId) {
        beginTransaction();

        FavoriteArtistRealm favorite = getArtist(artistId);
        favorite.deleteFromRealm();

        commitTransaction();
    }

    public void remove(List<String> artistIdList) {
        beginTransaction();

        realm.where(FavoriteArtistRealm.class)
                .in(FavoriteArtistRealm.ARTIST_ID, artistIdList.toArray(new String[0]))
                .findAll().deleteAllFromRealm();

        commitTransaction();
    }

    /**
     * truncateしてlistを登録する
     *
     * @param artistIdList
     */
    public void allUpdate(List<String> artistIdList) {
        beginTransaction();

        realm.delete(FavoriteArtistRealm.class);

        Stream.of(artistIdList).forEachIndexed((i, id) -> {
            realm.createObject(FavoriteArtistRealm.class, i + 1).setArtistId(id);
        });

        commitTransaction();
    }

    private int nextId() {
        Number maxId = realm.where(FavoriteArtistRealm.class).max(FavoriteArtistRealm.ID);

        return (maxId != null) ? maxId.intValue() + 1 : 1;
    }
}
