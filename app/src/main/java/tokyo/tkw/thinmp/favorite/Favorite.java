package tokyo.tkw.thinmp.favorite;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Favorite extends RealmObject {
    @PrimaryKey
    private String trackId;
    private Date createdAt = new Date();

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }
}
