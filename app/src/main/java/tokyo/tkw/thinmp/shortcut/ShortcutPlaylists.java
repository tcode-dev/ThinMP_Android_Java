package tokyo.tkw.thinmp.shortcut;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.playlist.Playlists;
import tokyo.tkw.thinmp.realm.ShortcutRealm;

class ShortcutPlaylists {
    private Context context;

    private ShortcutPlaylists(Context context) {
        this.context = context;
    }

    static ShortcutPlaylists createInstance(Context context) {
        return new ShortcutPlaylists(context);
    }

    Map<String, Shortcut> getPlaylistShortcutMap() {
        RealmResults<ShortcutRealm> realmResults = findAll();
        List<ShortcutRealm> shortcutRealmList = Stream.of(realmResults).toList();
        List<String> playlistIdList = getItemIdList(shortcutRealmList);
        List<Playlist> playLists = getPlayList(playlistIdList);
        Map<String, Playlist> playlistMap = getPlaylistMap(playLists);

        return toPlaylistShortcutMap(shortcutRealmList, playlistMap);
    }

    private RealmResults<ShortcutRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(ShortcutRealm.class).equalTo(ShortcutRealm.TYPE, ShortcutRealm.TYPE_PLAYLIST).findAll();
    }

    private List<String> getItemIdList(List<ShortcutRealm> shortcutRealmList) {
        return Stream.of(shortcutRealmList).map(ShortcutRealm::getItemId).toList();
    }

    private List<Playlist> getPlayList(List<String> playlistIdList) {
        Playlists playlists = Playlists.createInstance(context, playlistIdList);

        return playlists.getPlaylists();
    }

    private Map<String, Playlist> getPlaylistMap(List<Playlist> playlists) {
        return Stream.of(playlists).collect(Collectors.toMap(Playlist::getId, playlist -> playlist));
    }

    private Map<String, Shortcut> toPlaylistShortcutMap(List<ShortcutRealm> shortcutRealmList,
                                                        Map<String, Playlist> playlistMap) {
        return Stream.of(shortcutRealmList)
                .filter(shortcutRealm -> playlistMap.containsKey(shortcutRealm.getItemId()))
                .collect(Collectors.toMap(
                        ShortcutRealm::getId,
                        shortcutRealm -> {
                            Playlist playlist = playlistMap.get(shortcutRealm.getItemId());
                            return new Shortcut(
                                    shortcutRealm.getItemId(),
                                    playlist.getName(),
                                    ShortcutRealm.PLAYLIST,
                                    playlist.getAlbumArtId()
                            );
                        }));
    }
}
