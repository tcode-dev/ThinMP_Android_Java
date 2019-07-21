package tokyo.tkw.thinmp.dto;

import com.annimon.stream.Optional;

import java.util.List;

import tokyo.tkw.thinmp.track.Track;

public class PlaylistDetailDto {
    public String playlistName;
    public String typeName;
    public Optional<String> albumArtId;
    public List<Track> trackList;
}
