package tokyo.tkw.thinmp.dto;

import java.util.Map;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;

public class FavoriteArtistsDto {
    public RealmResults<FavoriteArtistRealm> realmResults;
    public Map<String, Artist> artistMap;
}
