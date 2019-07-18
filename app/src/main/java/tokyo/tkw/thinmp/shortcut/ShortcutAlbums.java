package tokyo.tkw.thinmp.shortcut;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.provider.AlbumContentProvider;
import tokyo.tkw.thinmp.realm.ShortcutRealm;

class ShortcutAlbums {
    private AlbumContentProvider provider;

    private ShortcutAlbums(Context context) {
        provider = new AlbumContentProvider(context);
    }

    static ShortcutAlbums createinstance(Context context) {
        return new ShortcutAlbums(context);
    }

    Map<Integer, Shortcut> getAlbumShortcutMap() {
        RealmResults<ShortcutRealm> realmResults = findAll();
        List<ShortcutRealm> shortcutRealmList = Stream.of(realmResults).toList();
        List<String> albumIdList = getItemIdList(realmResults);
        List<Album> albumList = getAlbumList(albumIdList);
        Map<String, Album> albumMap = getAlbumMap(albumList);

        return toAlbumShortcutMap(shortcutRealmList, albumMap);
    }

    private RealmResults<ShortcutRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(ShortcutRealm.class).equalTo(ShortcutRealm.TYPE, ShortcutRealm.TYPE_ALBUM).findAll();
    }

    private List<String> getItemIdList(List<ShortcutRealm> shortcutRealmList) {
        return Stream.of(shortcutRealmList).map(ShortcutRealm::getItemId).toList();
    }

    private List<Album> getAlbumList(List<String> albumIdList) {
        return provider.findById(albumIdList);
    }

    private Map<String, Album> getAlbumMap(List<Album> albumList) {
        return Stream.of(albumList).collect(Collectors.toMap(Album::getId, album -> album));
    }

    private Map<Integer, Shortcut> toAlbumShortcutMap(List<ShortcutRealm> shortcutRealmList,
                                                      Map<String, Album> albumMap) {
        return Stream.of(shortcutRealmList)
                .collect(Collectors.toMap(
                        ShortcutRealm::getId,
                        shortcutRealm -> {
                            Album album = albumMap.get(shortcutRealm.getItemId());
                            return new Shortcut(
                                    shortcutRealm.getItemId(),
                                    album.getName(),
                                    ShortcutRealm.ALBUM,
                                    album.getAlbumArtId()
                            );
                        }));
    }
}
