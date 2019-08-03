package tokyo.tkw.thinmp.register.edit;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.register.RealmRegister;
import tokyo.tkw.thinmp.register.delete.PlaylistDeleter;

public class PlaylistsEditor extends RealmRegister {
    public static PlaylistsEditor createInstance() {
        return new PlaylistsEditor();
    }

    public void update(List<String> fromPlaylistIdList, List<String> toPlaylistIdList) {
        beginTransaction();

        List<String> deletePlaylistIdList = getDeletePlaylistIdList(fromPlaylistIdList, toPlaylistIdList);

        delete(deletePlaylistIdList);
        sort(toPlaylistIdList);

        commitTransaction();
    }

    private List<String> getDeletePlaylistIdList(List<String> fromPlaylistIdList, List<String> toPlaylistIdList) {
        HashSet<String> toSet = new HashSet<>(toPlaylistIdList);

        return Stream.of(fromPlaylistIdList)
                .filter(playlistId -> (!toSet.contains(playlistId)))
                .toList();
    }

    private void delete(List<String> playlistIdList) {
        Optional.ofNullable(playlistIdList).ifPresent(idList -> {
            PlaylistDeleter playlistDeleter = PlaylistDeleter.createInstance();
            playlistDeleter.temporaryDelete(idList);
        });
    }

    private void sort(List<String> playlistIdList) {
        RealmResults<PlaylistRealm> realmResults = realm.where(PlaylistRealm.class)
                .in(PlaylistRealm.ID, playlistIdList.toArray(new String[0]))
                .findAll();

        Map<String, PlaylistRealm> map = Stream.of(realmResults)
                .collect(Collectors.toMap(PlaylistRealm::getId, playlistRealm -> playlistRealm));

        Stream.of(playlistIdList).forEachIndexed((i, playlistId) -> {
            Optional.ofNullable(map.get(playlistId)).ifPresent(playlistRealm -> playlistRealm.setOrder(i + 1));
        });
    }
}
