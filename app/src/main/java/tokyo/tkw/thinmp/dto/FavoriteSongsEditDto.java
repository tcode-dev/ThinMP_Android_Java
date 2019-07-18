package tokyo.tkw.thinmp.dto;

import java.util.List;
import java.util.Map;

import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;

public class FavoriteSongsEditDto {
    public List<FavoriteSongRealm> favoriteList;
    public Map<String, Track> trackMap;
}
