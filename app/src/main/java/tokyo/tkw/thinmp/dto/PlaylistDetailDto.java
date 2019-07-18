package tokyo.tkw.thinmp.dto;

import com.annimon.stream.Optional;

import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistDetailDto {
    public String playlistName;
    public String typeName;
    public Optional<String> albumArtId;
    public RealmList<PlaylistTrackRealm> trackRealmList;
    public Map<String, Track> trackMap;
}
