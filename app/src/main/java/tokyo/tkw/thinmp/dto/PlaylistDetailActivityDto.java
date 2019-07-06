package tokyo.tkw.thinmp.dto;

import com.annimon.stream.Optional;

import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistDetailActivityDto {
    public String playlistName;
    public String typeName;
    public String albumArt;
    public RealmList<PlaylistTrackRealm> playlistTrackRealms;
    public Map<String, Track> trackMap;
}
