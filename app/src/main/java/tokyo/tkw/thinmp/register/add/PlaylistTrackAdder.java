package tokyo.tkw.thinmp.register.add;

import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.register.RealmRegister;

public class PlaylistTrackAdder extends RealmRegister {
    public static PlaylistTrackAdder createInstance() {
        return new PlaylistTrackAdder();
    }

    public List<PlaylistTrackRealm> create(String playlistId, List<String> trackIdList) {
        return Stream.of(trackIdList).map(trackId -> {
            PlaylistTrackRealm playlistTrackRealm = realm.createObject(PlaylistTrackRealm.class, uuid());
            playlistTrackRealm.set(playlistId, trackId);
            return playlistTrackRealm;
        }).toList();
    }
}
