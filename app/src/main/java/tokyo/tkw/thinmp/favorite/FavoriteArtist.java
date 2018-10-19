package tokyo.tkw.thinmp.favorite;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteArtist extends RealmObject {
    @PrimaryKey
    private String artistId;
    private Date createdAt = new Date();

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }
}
