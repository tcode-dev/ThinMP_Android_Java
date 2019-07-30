package tokyo.tkw.thinmp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FavoriteArtistRealm extends RealmObject {
    public static final String ID = "id";
    public static final String ARTIST_ID = "artistId";
    public static final String ORDER = "order";

    @PrimaryKey
    private String id;
    private String artistId;
    private int order;

    public String getArtistId() {
        return artistId;
    }

    public void set(String artistId, int order) {
        this.artistId = artistId;
        this.order = order;
    }
}
