package tokyo.tkw.thinmp.register.edit;

import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class FavoriteArtistEditor extends RealmRegister {
    public static FavoriteArtistEditor createInstance() {
        return new FavoriteArtistEditor();
    }

    public void update(List<String> artistIdList) {
        beginTransaction();

        truncate();
        addAll(artistIdList);

        commitTransaction();
    }

    private void truncate() {
        realm.delete(FavoriteArtistRealm.class);
    }

    private void addAll(List<String> artistIdList) {
        Stream.of(artistIdList).forEach((id) -> {
            realm.createObject(FavoriteArtistRealm.class, uuid())
                    .set(id, increment(FavoriteArtistRealm.class, FavoriteArtistRealm.ORDER));
        });
    }
}
