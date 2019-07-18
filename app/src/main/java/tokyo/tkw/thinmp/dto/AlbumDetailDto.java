package tokyo.tkw.thinmp.dto;

import com.annimon.stream.Optional;

import java.util.List;

import tokyo.tkw.thinmp.track.Track;

public class AlbumDetailDto {
    public String albumName;
    public String artistName;
    public Optional<String> albumArtId;
    public List<Track> trackList;
}
