package tokyo.tkw.thinmp.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ShortcutRealm extends RealmObject {
    public static final String ARTIST = "artist";
    public static final String ALBUM = "album";
    public static final String PLAYLIST = "playlist";
    public static final String ID = "id";
    public static final String ITEM_ID = "itemId";
    public static final String TYPE = "type";
    public static final String ORDER = "order";

    public static final int TYPE_ARTIST = 1;
    public static final int TYPE_ALBUM = 2;
    public static final int TYPE_PLAYLIST = 3;

    @PrimaryKey
    private String id;
    // artist_id or album_id or playlist_id
    private String itemId;
    private int type;
    private int order;

    public void set(String itemId, int type, int order) {
        this.itemId = itemId;
        this.type = type;
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public String getItemId() {
        return itemId;
    }

    public int getType() {
        return type;
    }
}

