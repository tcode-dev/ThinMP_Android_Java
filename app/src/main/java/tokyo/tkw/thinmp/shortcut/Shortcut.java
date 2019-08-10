package tokyo.tkw.thinmp.shortcut;

public class Shortcut {
    private String id;
    private String itemId;
    private String name;
    private String type;
    private String albumArtId;

    Shortcut(String id, String itemId, String name, String type, String albumArtId) {
        this.id = id;
        this.itemId = itemId;
        this.name = name;
        this.type = type;
        this.albumArtId = albumArtId;
    }

    public String getId() {
        return id;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAlbumArtId() {
        return albumArtId;
    }
}
