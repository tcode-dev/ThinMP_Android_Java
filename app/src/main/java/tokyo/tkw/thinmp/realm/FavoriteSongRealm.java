package tokyo.tkw.thinmp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteSongRealm extends RealmObject {
    public static final String ID = "id";
    public static final String TRACK_ID = "trackId";

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
