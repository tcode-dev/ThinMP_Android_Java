package tokyo.tkw.thinmp.registration.edit;

import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.realm.FavoriteSongRealm;
import tokyo.tkw.thinmp.registration.RealmRegister;

public class FavoriteSongEditor extends RealmRegister {
    public static FavoriteSongEditor createInstance() {
        return new FavoriteSongEditor();
    }

    public void update(List<String> trackIdList) {
        beginTransaction();

        truncate();
        addAll(trackIdList);

        commitTransaction();
    }

    private void truncate() {
        realm.delete(FavoriteSongRealm.class);
    }

    private void addAll(List<String> trackIdList) {
        Stream.of(trackIdList).forEach((trackId) -> {
            realm.createObject(FavoriteSongRealm.class, uuid())
                    .set(trackId, increment(FavoriteSongRealm.class, FavoriteSongRealm.ORDER));
        });
    }
}
