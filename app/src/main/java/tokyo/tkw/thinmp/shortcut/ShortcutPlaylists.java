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
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.ShortcutRealm;

public class ShortcutPlaylists {
    private Context context;

    private ShortcutPlaylists(Context context) {
        this.context = context;
    }

    static ShortcutPlaylists createInstance(Context context) {
        return new ShortcutPlaylists(context);
    }

    Map<Integer, Shortcut> getPlaylistShortcutMap() {
        RealmResults<ShortcutRealm> realmResults = findAll();
        List<ShortcutRealm> shortcutRealmList = Stream.of(realmResults).toList();
        List<String> playlistIdList = getItemIdList(shortcutRealmList);
        List<PlaylistRealm> playLists = getPlayList(playlistIdList);
        Map<String, PlaylistRealm> playlistMap = getPlaylistMap(playLists);

        return toPlaylistShortcutMap(shortcutRealmList, playlistMap);
    }

    private RealmResults<ShortcutRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(ShortcutRealm.class).equalTo(ShortcutRealm.TYPE, ShortcutRealm.TYPE_PLAYLIST).findAll();
    }

    private List<String> getItemIdList(List<ShortcutRealm> shortcutRealmList) {
        return Stream.of(shortcutRealmList).map(ShortcutRealm::getItemId).toList();
    }

    private List<PlaylistRealm> getPlayList(List<String> playlistIdList) {
        Playlists playlists = Playlists.createInstance(context, playlistIdList);

        return playlists.getPlaylistRealmList();
    }

    private Map<String, PlaylistRealm> getPlaylistMap(List<PlaylistRealm> playlists) {
        return Stream.of(playlists)
                .collect(Collectors.toMap(
                        playlistRealm -> playlistRealm.getId(),
                        playlistRealm -> playlistRealm));
    }

    private Map<Integer, Shortcut> toPlaylistShortcutMap(List<ShortcutRealm> shortcutRealmList,
                                                         Map<String, PlaylistRealm> playlistMap) {
        return Stream.of(shortcutRealmList)
                .collect(Collectors.toMap(
                        ShortcutRealm::getId,
                        shortcutRealm -> {
                            PlaylistRealm playlistRealm =
                                    playlistMap.get(shortcutRealm.getItemId());
                            Playlist playlist = Playlist.createInstance(context,
                                    playlistRealm.getId());
                            return new Shortcut(
                                    shortcutRealm.getItemId(),
                                    playlistRealm.getName(),
                                    ShortcutRealm.PLAYLIST,
                                    playlist.getAlbumArtId()
                            );
                        }));
    }
}
