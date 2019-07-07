package tokyo.tkw.thinmp.dto;

import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistDetailDto {
    public String playlistName;
    public String typeName;
    public String albumArtId;
    public RealmList<PlaylistTrackRealm> playlistTrackRealms;
    public Map<String, Track> trackMap;
}
