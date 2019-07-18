package tokyo.tkw.thinmp.dto;

import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistDetailEditDto {
    public String playlistName;
    public RealmList<PlaylistTrackRealm> trackRealmList;
    public Map<String, Track> trackMap;
    public PlaylistRealm playlistRealm;
}
