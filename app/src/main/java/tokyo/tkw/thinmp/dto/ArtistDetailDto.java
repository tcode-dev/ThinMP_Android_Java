package tokyo.tkw.thinmp.dto;

import com.annimon.stream.Optional;

import java.util.List;

import tokyo.tkw.thinmp.album.Album;
import tokyo.tkw.thinmp.track.Track;

public class ArtistDetailDto {
    public String artistName;
    public String albumsTitle;
    public String songsTitle;
    public String meta;
    public List<Album> albumList;
    public List<Track> trackList;
    public Optional<String> albumArtId;
    public int layoutSpanSize;
    public int headerSpanSize;
    public int albumListSpanSize;
    public int trackListSpanSize;
}
