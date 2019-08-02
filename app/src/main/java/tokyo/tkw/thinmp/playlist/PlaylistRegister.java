package tokyo.tkw.thinmp.playlist;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Music;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.register.RealmRegister;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.shortcut.ShortcutRegister;

public class PlaylistRegister extends RealmRegister {
    private PlaylistRegister() {
        super();
    }

    public static PlaylistRegister createInstance() {
        return new PlaylistRegister();
    }

    public void create(String name, Music music) {
        int nextOrder = getNextPlaylistOrder();

        beginTransaction();

        PlaylistRealm playlist = realm.createObject(PlaylistRealm.class, uuid());
        playlist.set(name, nextOrder, createPlaylistTrackRealmList(playlist.getId(), music.getIdList()));

        commitTransaction();
    }

    public void add(String playlistId, Music music) {
        beginTransaction();

        PlaylistRealm playlist = getPlaylist(playlistId);

        List<PlaylistTrackRealm> playlistTrackRealmList = createPlaylistTrackRealmList(
                playlistId,
                music.getIdList()
        );

        playlist.getTrackRealmList().addAll(playlistTrackRealmList);

        commitTransaction();
    }

    public void delete(List<String> trackIdList) {
        beginTransaction();

        temporaryDelete(trackIdList);

        commitTransaction();
    }

    private void temporaryDelete(List<String> trackIdList) {
        realm.where(PlaylistTrackRealm.class)
                .in(PlaylistTrackRealm.TRACK_ID, trackIdList.toArray(new String[0]))
                .findAll()
                .deleteAllFromRealm();
    }


    private void temporaryDeletePlaylist(List<String> playlistIdList) {
        RealmResults<PlaylistRealm> realmResults = realm.where(PlaylistRealm.class)
                .in(PlaylistRealm.ID, playlistIdList.toArray(new String[0]))
                .findAll();

        // 削除
        ShortcutRegister shortcutRegister = ShortcutRegister.createInstance();

        Stream.of(realmResults).forEach(playlistRealm -> {
            // shortcutに登録されていたら削除する
            String playListId = playlistRealm.getId();

            if (shortcutRegister.exists(playListId, ShortcutRealm.TYPE_PLAYLIST)) {
                shortcutRegister.temporaryDelete(playListId, ShortcutRealm.TYPE_PLAYLIST);
            }

            // プレイリストの曲を削除する
            playlistRealm.getTrackRealmList().deleteAllFromRealm();

            // プレイリストを削除する
            playlistRealm.deleteFromRealm();
        });
    }

    /**
     * プレイリスト詳細の編集
     *
     * @param playlistId
     * @param name
     * @param fromTrackIdList
     * @param toTrackIdList
     */
    public void update(String playlistId, String name, List<String> fromTrackIdList, List<String> toTrackIdList) {
        beginTransaction();

        PlaylistRealm playlist = getPlaylist(playlistId);

        playlist.setName(name);

        temporaryDelete(fromTrackIdList);

        List<PlaylistTrackRealm> playlistTrackRealmList = createPlaylistTrackRealmList(
                playlistId,
                toTrackIdList
        );

        playlist.getTrackRealmList().addAll(playlistTrackRealmList);

        commitTransaction();
    }

    public void update(List<String> fromPlaylistIdList, List<String> toPlaylistIdList) {
        beginTransaction();

        HashSet<String> toSet = new HashSet<>(toPlaylistIdList);

        List<String> deletePlaylistIdList = Stream.of(fromPlaylistIdList)
                .filter(playlistId -> (!toSet.contains(playlistId)))
                .toList();

        // 削除
        Optional.ofNullable(deletePlaylistIdList).ifPresent(this::temporaryDeletePlaylist);

        // 並び替え
        RealmResults<PlaylistRealm> realmResults = realm.where(PlaylistRealm.class)
                .in(PlaylistRealm.ID, toPlaylistIdList.toArray(new String[0]))
                .findAll();

        Map<String, PlaylistRealm> map = Stream.of(realmResults)
                .collect(Collectors.toMap(PlaylistRealm::getId, playlistRealm -> playlistRealm));

        Stream.of(toPlaylistIdList).forEachIndexed((i, playlistId) -> {
            Optional.ofNullable(map.get(playlistId)).ifPresent(playlistRealm -> playlistRealm.setOrder(i + 1));
        });

        commitTransaction();
    }

    private PlaylistRealm getPlaylist(String playlistId) {
        return realm.where(PlaylistRealm.class).equalTo(PlaylistRealm.ID, playlistId).findFirst();
    }

    private List<PlaylistTrackRealm> createPlaylistTrackRealmList(String playlistId, List<String> trackIdList) {
        return Stream.of(trackIdList).map(trackId -> {
            PlaylistTrackRealm playlistTrackRealm = realm.createObject(PlaylistTrackRealm.class, uuid());
            playlistTrackRealm.set(playlistId, trackId);
            return playlistTrackRealm;
        }).toList();
    }

    private int getNextPlaylistOrder() {
        Number max = realm.where(PlaylistRealm.class).max(PlaylistRealm.ORDER);
        return (max != null) ? max.intValue() + 1 : 1;
    }
}
