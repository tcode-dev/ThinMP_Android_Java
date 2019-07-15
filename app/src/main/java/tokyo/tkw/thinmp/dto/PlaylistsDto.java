package tokyo.tkw.thinmp.dto;

import java.util.Map;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class PlaylistsDto {
    public RealmResults<PlaylistRealm> realmResults;
    public Map<Integer, Playlist> playlistMap;
}
