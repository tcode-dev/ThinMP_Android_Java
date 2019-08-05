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
        Map<String, Shortcut> shortcutMap = getShortcutMap();

        return toShortcutList(realmResults, shortcutMap);
    }

    private Map<String, Shortcut> getShortcutMap() {
        Map<String, Shortcut> artistMap = shortcutArtists.getArtistShortcutMap();
        Map<String, Shortcut> albumMap = shortcutAlbums.getAlbumShortcutMap();
        Map<String, Shortcut> playlistMap = shortcutPlaylists.getPlaylistShortcutMap();

        return mergeShortcutMap(artistMap, albumMap, playlistMap);
    }

    private Map<String, Shortcut> mergeShortcutMap(
            Map<String, Shortcut> artistMap,
            Map<String, Shortcut> albumMap,
            Map<String, Shortcut> playlistMap
    ) {
        return Stream.of(artistMap, albumMap, playlistMap)
                .flatMap(map -> Stream.of(map.entrySet()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private List<Shortcut> toShortcutList(RealmResults<ShortcutRealm> realmResults,
                                          Map<String, Shortcut> shortcutMap) {
        return Stream.of(realmResults)
                .filter(shortcutRealm -> shortcutMap.containsKey(shortcutRealm.getId()))
                .map(shortcutRealm -> shortcutMap.get(shortcutRealm.getId()))
                .toList();
    }

    private RealmResults<ShortcutRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(ShortcutRealm.class).findAll().sort(ShortcutRealm.ORDER, Sort.DESCENDING);
    }
}
