package tokyo.tkw.thinmp.dto;

import java.util.Map;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;

public class FavoriteSongsDto {
    public RealmResults<FavoriteSongRealm> realmResults;
    public Map<String, Track> trackMap;
}
