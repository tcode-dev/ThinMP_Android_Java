package tokyo.tkw.thinmp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteArtistRealm extends RealmObject {
    @PrimaryKey
    private int id;
    private String artistId;

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }
}
