package tokyo.tkw.thinmp.favorite;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteArtist extends RealmObject {
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
