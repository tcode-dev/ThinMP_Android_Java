package tokyo.tkw.thinmp.shortcut;

import com.annimon.stream.Optional;

public class Shortcut {
    public String id;
    public String name;
    public String type;
    public Optional<String> albumArtId;

    public Shortcut(String id, String name, String type, Optional<String> albumArtId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.albumArtId = albumArtId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Optional<String> getAlbumArtId() {
        return albumArtId;
    }
}
