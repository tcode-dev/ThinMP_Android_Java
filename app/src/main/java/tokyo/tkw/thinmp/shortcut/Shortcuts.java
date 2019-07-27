package tokyo.tkw.thinmp.shortcut;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import tokyo.tkw.thinmp.realm.ShortcutRealm;

public class Shortcuts {
    private ShortcutArtists shortcutArtists;
    private ShortcutAlbums shortcutAlbums;
    private ShortcutPlaylists shortcutPlaylists;

    private Shortcuts(Context context) {
        this.shortcutArtists = ShortcutArtists.createInstance(context);
        this.shortcutAlbums = ShortcutAlbums.createInstance(context);
        this.shortcutPlaylists = ShortcutPlaylists.createInstance(context);
    }

    public static Shortcuts createInstance(Context context) {
        return new Shortcuts(context);
    }

    public List<Shortcut> getList() {
        RealmResults<ShortcutRealm> realmResults = findAll();
        Map<Integer, Shortcut> shortcutMap = getShortcutMap();

        return toShortcutList(realmResults, shortcutMap);
    }

    private Map<Integer, Shortcut> getShortcutMap() {
        Map<Integer, Shortcut> artistMap = shortcutArtists.getArtistShortcutMap();
        Map<Integer, Shortcut> albumMap = shortcutAlbums.getAlbumShortcutMap();
        Map<Integer, Shortcut> playlistMap = shortcutPlaylists.getPlaylistShortcutMap();

        return mergeShortcutMap(artistMap, albumMap, playlistMap);
    }

    private Map<Integer, Shortcut> mergeShortcutMap(
            Map<Integer, Shortcut> artistMap,
            Map<Integer, Shortcut> albumMap,
            Map<Integer, Shortcut> playlistMap
    ) {
        return Stream.of(artistMap, albumMap, playlistMap)
                .flatMap(map -> Stream.of(map.entrySet()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Shortcut> toShortcutList(RealmResults<ShortcutRealm> realmResults,
                                          Map<Integer, Shortcut> shortcutMap) {
        return Stream.of(realmResults).map(shortcutRealm -> shortcutMap.get(shortcutRealm.getId())).toList();
    }

    private RealmResults<ShortcutRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(ShortcutRealm.class).findAll().sort(ShortcutRealm.ID, Sort.DESCENDING);
    }
}
