package tokyo.tkw.thinmp.favorite;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Favorite extends RealmObject {
    @PrimaryKey
    private String track_id;

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public void set(String track_id) {
        setTrack_id(track_id);
    }
}
