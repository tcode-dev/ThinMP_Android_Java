package tokyo.tkw.thinmp.favorite;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteSong extends RealmObject {
    @PrimaryKey
    private int id;
    private String trackId;

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
}
