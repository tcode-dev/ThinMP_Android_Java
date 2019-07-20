package tokyo.tkw.thinmp.shortcut;

import android.content.Context;

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
        this.shortcutAlbums = ShortcutAlbums.createinstance(context);
        this.shortcutPlaylists = ShortcutPlaylists.createInstance(context);
    }

    public static Shortcuts createInstance(Context context) {
        return new Shortcuts(context);
    }

    public List<Shortcut> getList() {
        Map<Integer, Shortcut> artistMap = shortcutArtists.getArtistShortcutMap();
        Map<Integer, Shortcut> albumMap = shortcutAlbums.getAlbumShortcutMap();
        Map<Integer, Shortcut> playlistMap = shortcutPlaylists.getPlaylistShortcutMap();
        RealmResults<ShortcutRealm> realmResults = findAll();

        return toShortcutList(realmResults, artistMap, albumMap, playlistMap);
    }

    private List<Shortcut> toShortcutList(RealmResults<ShortcutRealm> realmResults,
                                          Map<Integer, Shortcut> artistMap,
                                          Map<Integer, Shortcut> albumMap,
                                          Map<Integer, Shortcut> playlistMap) {
        return Stream.of(realmResults).map(shortcutRealm -> {
            switch (shortcutRealm.getType()) {
                case ShortcutRealm.TYPE_ARTIST:
                    return artistMap.get(shortcutRealm.getId());

                case ShortcutRealm.TYPE_ALBUM:
                    return albumMap.get(shortcutRealm.getId());

                case ShortcutRealm.TYPE_PLAYLIST:
                    return playlistMap.get(shortcutRealm.getId());

                default:
                    return null;
            }
        }).toList();
    }

    private RealmResults<ShortcutRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(ShortcutRealm.class).findAll().sort(ShortcutRealm.ID, Sort.DESCENDING);
    }
}
