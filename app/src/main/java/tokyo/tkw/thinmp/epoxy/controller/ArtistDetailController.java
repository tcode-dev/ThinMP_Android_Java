package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

import tokyo.tkw.thinmp.epoxy.model.AlbumListItemModel_;
import tokyo.tkw.thinmp.epoxy.model.HeaderModel_;
import tokyo.tkw.thinmp.epoxy.model.TrackListItemModel_;
import tokyo.tkw.thinmp.listener.AlbumClickListener;
import tokyo.tkw.thinmp.listener.EpoxyTrackClickListener;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Track;

public class ArtistDetailController extends TypedEpoxyController<ArtistDetailController.Data> {
    @AutoModel
    HeaderModel_ headerModel;
    @AutoModel
    AlbumListItemModel_ artistListItemModel;
    @AutoModel
    TrackListItemModel_ trackListItemModel;

    @Override
    protected void buildModels(Data data) {
        new HeaderModel_()
                .id("albums")
                .title(data.albums)
                .spanSizeOverride((totalSpanCount, position, itemCount) -> data.titleSpanSize)
                .addTo(this);

        Stream.of(data.albumList).forEachIndexed((i, album) -> {
            new AlbumListItemModel_()
                    .id(album.getId())
                    .albumName(album.getName())
                    .albumArtId(album.getAlbumArtId())
                    .clickListener(new AlbumClickListener(album.getId()))
                    .spanSizeOverride((totalSpanCount, position, itemCount) -> data.albumListSpanSize)
                    .addTo(this);
        });

        new HeaderModel_()
                .id("tracks")
                .title(data.songs)
                .spanSizeOverride((totalSpanCount, position, itemCount) -> data.titleSpanSize)
                .addTo(this);

        Stream.of(data.trackList).forEachIndexed((i, track) -> {
            new TrackListItemModel_()
                    .id(track.getId())
                    .trackName(track.getTitle())
                    .albumArtId(track.getAlbumArtId())
                    .spanSizeOverride((totalSpanCount, position, itemCount) -> data.trackListSpanSize)
                    .clickListener(new EpoxyTrackClickListener(data.trackList, i))
                    .addTo(this);
        });
    }

    public static class Data {
        public String albums;
        public List<Album> albumList;
        public String songs;
        public List<Track> trackList;
        public int titleSpanSize;
        public int albumListSpanSize;
        public int trackListSpanSize;
    }
}
