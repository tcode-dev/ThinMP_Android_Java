package tokyo.tkw.thinmp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteSongRealm extends RealmObject {
    public static final String ID = "id";
    public static final String TRACK_ID = "trackId";
    public static final String ORDER = "order";

    @PrimaryKey
    private String id;
    private String trackId;
    private int order;

    public String getTrackId() {
        return trackId;
    }

    public void set(String trackId, int order) {
        this.trackId = trackId;
        this.order = order;
    }
}
