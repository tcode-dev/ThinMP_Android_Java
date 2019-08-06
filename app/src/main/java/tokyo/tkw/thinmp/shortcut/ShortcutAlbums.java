package tokyo.tkw.thinmp.shortcut;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.provider.AlbumContentProvider;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.register.delete.ShortcutDeleter;

class ShortcutAlbums {
    private AlbumContentProvider provider;
    private RealmResults<ShortcutRealm> realmResults;
    private List<ShortcutRealm> shortcutRealmList;
    private List<String> albumIdList;
    private List<Album> albumList;
    private Map<String, Album> albumMap;
    private Map<String, Shortcut> shortcutMap;

    private ShortcutAlbums(Context context) {
        provider = new AlbumContentProvider(context);
    }

    static ShortcutAlbums createInstance(Context context) {
        return new ShortcutAlbums(context);
    }

    Map<String, Shortcut> getAlbumShortcutMap() {
        realmResults = findAll();
        shortcutRealmList = getShortcutRealmList();
        albumIdList = getItemIdList();
        albumList = getAlbumList();
        albumMap = getAlbumMap();
        shortcutMap = getShortcutMap();

        validation();

        return shortcutMap;
    }

    private RealmResults<ShortcutRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(ShortcutRealm.class).equalTo(ShortcutRealm.TYPE, ShortcutRealm.TYPE_ALBUM).findAll();
    }

    private List<ShortcutRealm> getShortcutRealmList() {
        return Stream.of(realmResults).toList();
    }

    private List<String> getItemIdList() {
        return Stream.of(shortcutRealmList).map(ShortcutRealm::getItemId).toList();
    }

    private List<Album> getAlbumList() {
        return provider.findById(albumIdList);
    }

    private Map<String, Album> getAlbumMap() {
        return Stream.of(albumList).collect(Collectors.toMap(Album::getId, album -> album));
    }

    private Map<String, Shortcut> getShortcutMap() {
        return Stream.of(shortcutRealmList)
                .filter(shortcutRealm -> albumMap.containsKey(shortcutRealm.getItemId()))
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

    private void validation() {
        if (isDeleted()) {
            delete();
        }
    }

    private boolean isDeleted() {
        return albumIdList.size() != albumList.size();
    }

    private void delete() {
        List<String> actualList = Stream.of(albumList).map(Album::getId).toList();
        List<String> removeList = Stream.of(albumIdList)
                .filter(id -> !actualList.contains(id))
                .collect(Collectors.toList());

        ShortcutDeleter shortcutDeleter = ShortcutDeleter.createInstance();
        shortcutDeleter.delete(removeList, ShortcutRealm.TYPE_ALBUM);
    }
}
