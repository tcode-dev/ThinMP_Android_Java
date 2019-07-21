package tokyo.tkw.thinmp.dto;

import java.util.List;
import java.util.Map;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.realm.PlaylistRealm;

public class PlaylistsDto {
    public String title;
    public List<Playlist> playlists;
    public int titleSpanSize;
    public int listSpanSize;
}
