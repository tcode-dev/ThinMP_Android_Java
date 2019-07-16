package tokyo.tkw.thinmp.dto;

import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class PlaylistsEditDto {
    public RealmList<PlaylistRealm> realmList;
    public Map<String, Playlist> playlistMap;
}
