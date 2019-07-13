package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.ArtistDetailDto;
import tokyo.tkw.thinmp.music.AlbumCollection;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.music.TrackCollection;

public class ArtistDetailLogic {
    private Context context;
    private Artist artist;
    private AlbumCollection albumCollection;
    private TrackCollection trackCollection;

    private ArtistDetailLogic(Context context, String artistId) {
        this.context = context;
        this.artist = Artist.createInstance(context, artistId);
        this.albumCollection = AlbumCollection.createInstance(context);
        this.trackCollection = TrackCollection.createArtistTrackCollectionInstance(context, artistId);
    }

    public static ArtistDetailLogic createInstance(Context context, String artistId) {
        return new ArtistDetailLogic(context, artistId);
    }
    public ArtistDetailDto createDto() {
        ArtistDetailDto dto = new ArtistDetailDto();

        dto.artistName = artist.getName();
        dto.albumsTitle = context.getResources().getString(R.string.albums);
        dto.songsTitle = context.getResources().getString(R.string.songs);
        dto.albumList = albumCollection.findByArtist(artist.getId());
        dto.trackList = trackCollection.getList();
        dto.albumArtId = albumCollection.findFirstAlbumArtId(dto.albumList);
        dto.titleSpanSize = 2;
        dto.albumListSpanSize = 1;
        dto.trackListSpanSize = 2;

        return dto;
    }
}
