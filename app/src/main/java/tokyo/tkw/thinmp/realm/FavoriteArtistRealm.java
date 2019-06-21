package tokyo.tkw.thinmp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteArtistRealm extends RealmObject {
    public static final String ID = "id";
    public static final String ARTIST_ID = "artistId";

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
