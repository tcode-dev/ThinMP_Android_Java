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
        return findFirst(artistId) != null;
    }

    public void add(String artistId) {
        beginTransaction();

        realm.createObject(FavoriteArtistRealm.class, uuid()).setArtistId(artistId);

        commitTransaction();
    }

    public void delete(String artistId) {
        beginTransaction();

        findFirst(artistId).deleteFromRealm();

        commitTransaction();
    }

    public void delete(List<String> artistIdList) {
        beginTransaction();

        realm.where(FavoriteArtistRealm.class)
                .in(FavoriteArtistRealm.ARTIST_ID, artistIdList.toArray(new String[0]))
                .findAll()
                .deleteAllFromRealm();

        commitTransaction();
    }

    /**
     * truncateしてlistを登録する
     *
     * @param artistIdList
     */
    public void update(List<String> artistIdList) {
        beginTransaction();

        realm.delete(FavoriteArtistRealm.class);

        Stream.of(artistIdList).forEach((id) -> {
            realm.createObject(FavoriteArtistRealm.class, uuid()).setArtistId(id);
        });

        commitTransaction();
    }

    private FavoriteArtistRealm findFirst(String artistId) {
        return realm.where(FavoriteArtistRealm.class).equalTo(FavoriteArtistRealm.ARTIST_ID, artistId).findFirst();
    }
}
