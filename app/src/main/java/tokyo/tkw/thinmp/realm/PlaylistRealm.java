package tokyo.tkw.thinmp.realm;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class PlaylistRealm extends RealmObject {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String ORDER = "order";
    public static final String TRACKS = "tracks";
    @PrimaryKey
    private String id;
    private String name;
    private int order;
    private RealmList<PlaylistTrackRealm> tracks;

    public void set(String name, List<PlaylistTrackRealm> trackList, int order) {
        this.name = name;
        this.order = order;
        this.tracks.addAll(trackList);
    }

    public RealmList<PlaylistTrackRealm> getTrackRealmList() {
        return tracks;
    }

    public void setTracks(RealmList<PlaylistTrackRealm> tracks) {
        this.tracks = tracks;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

