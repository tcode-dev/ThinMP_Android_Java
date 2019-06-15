package tokyo.tkw.thinmp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteSongRealm extends RealmObject {
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
