package tokyo.tkw.thinmp.favorite;

import com.annimon.stream.IntStream;

import java.util.List;

import io.realm.Realm;
import tokyo.tkw.thinmp.util.ActivityUtil;

public class FavoriteArtistRegister {
    private Realm mRealm;

    public FavoriteArtistRegister() {
        Realm.init(ActivityUtil.getContext());
        mRealm = Realm.getDefaultInstance();
    }

    public static FavoriteArtistRegister createInstance() {
        return new FavoriteArtistRegister();
    }

    public static boolean set(String artistId) {
        FavoriteArtistRegister favoriteRegister = FavoriteArtistRegister.createInstance();

        return favoriteRegister.update(artistId);
    }

    public static boolean exists(String artistId) {
        FavoriteArtistRegister favoriteRegister = FavoriteArtistRegister.createInstance();

        return favoriteRegister.getArtist(artistId) != null;
    }

    public static void update(List list) {
        FavoriteArtistRegister favoriteRegister = FavoriteArtistRegister.createInstance();
        favoriteRegister.allUpdate(list);
    }

    public void beginTransaction() {
        mRealm.beginTransaction();
    }

    public void commitTransaction() {
        mRealm.commitTransaction();
    }

    private FavoriteArtist getArtist(String artistId) {
        return mRealm.where(FavoriteArtist.class).equalTo("artistId", artistId).findFirst();
    }

    private boolean update(String artistId) {
        FavoriteArtist favorite = getArtist(artistId);

        beginTransaction();

        boolean isFavorite;
        if (favorite == null) {
            mRealm.createObject(FavoriteArtist.class, nextId()).setArtistId(artistId);
            isFavorite = true;
        } else {
            favorite.deleteFromRealm();
            isFavorite = false;
        }

        commitTransaction();

        return isFavorite;
    }

    /**
     * truncateしてlistを登録する
     *
     * @param list
     */
    private void allUpdate(List list) {
        beginTransaction();

        mRealm.delete(FavoriteArtist.class);

        IntStream.range(0, list.size()).forEach(i -> mRealm.createObject(FavoriteArtist.class, i + 1).setArtistId(list.get(i).toString()));

        commitTransaction();
    }

    private int nextId() {
        Number maxId = mRealm.where(FavoriteArtist.class).max("id");

        return (maxId != null) ? maxId.intValue() + 1 : 1;
    }
}
