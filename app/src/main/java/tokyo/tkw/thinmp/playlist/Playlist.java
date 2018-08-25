package tokyo.tkw.thinmp.playlist;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Playlist extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private int order;
    private RealmList<PlaylistTrack> tracks;

    public RealmList<PlaylistTrack> getTracks() {
        return tracks;
    }

    public void setTracks(RealmList<PlaylistTrack> tracks) {
        this.tracks = tracks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

