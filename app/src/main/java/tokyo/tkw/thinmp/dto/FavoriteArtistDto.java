package tokyo.tkw.thinmp.dto;

import java.util.List;
import java.util.Map;

import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;

public class FavoriteArtistDto {
    public List<FavoriteArtistRealm> favoriteList;
    public Map<String, Artist> artistMap;
}
