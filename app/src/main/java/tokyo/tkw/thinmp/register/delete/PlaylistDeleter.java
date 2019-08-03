package tokyo.tkw.thinmp.register.delete;

import com.annimon.stream.Stream;

import java.util.List;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.register.RealmRegister;
import tokyo.tkw.thinmp.register.exists.ShortcutExists;

public class PlaylistDeleter extends RealmRegister {
    public static PlaylistDeleter createInstance() {
        return new PlaylistDeleter();
    }

    public void temporaryDelete(List<String> playlistIdList) {
        RealmResults<PlaylistRealm> realmResults = findByPlaylistId(playlistIdList);

        // 削除
        Stream.of(realmResults).forEach(playlistRealm -> {
            // playListId
            String playListId = playlistRealm.getId();

            // shortcutに登録されていたら削除する
            deleteShortcutIfNeeded(playListId);

            // プレイリストの曲を削除する
            playlistRealm.getTrackRealmList().deleteAllFromRealm();

            // プレイリストを削除する
            playlistRealm.deleteFromRealm();
        });
    }

    private void deleteShortcutIfNeeded(String playListId) {
        ShortcutExists shortcutExists = ShortcutExists.createInstance();
        if (!shortcutExists.exists(playListId, ShortcutRealm.TYPE_PLAYLIST)) return;

        ShortcutDeleter shortcutDeleter = ShortcutDeleter.createInstance();
        shortcutDeleter.temporaryDelete(playListId, ShortcutRealm.TYPE_PLAYLIST);
    }

    private RealmResults<PlaylistRealm> findByPlaylistId(List<String> playlistIdList) {
        return realm.where(PlaylistRealm.class)
                .in(PlaylistRealm.ID, playlistIdList.toArray(new String[0]))
                .findAll();
    }
}
