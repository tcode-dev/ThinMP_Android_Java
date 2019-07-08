package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.epoxy.model.ArtistListItemModel_;
import tokyo.tkw.thinmp.epoxy.model.HeaderModel_;
import tokyo.tkw.thinmp.listener.ArtistClickListener;
import tokyo.tkw.thinmp.music.Artist;

public class ArtistsController extends TypedEpoxyController<ArtistsController.Data> {
    @AutoModel
    HeaderModel_ headerModel;
    @AutoModel
    ArtistListItemModel_ artistListItemModel;

    @Override
    protected void buildModels(Data data) {
        new HeaderModel_().id("header").title(data.title).addTo(this);

        Stream.of(data.artistList).forEach(artist -> {
            new ArtistListItemModel_()
                    .id(artist.getId())
                    .artistName(artist.getName())
                    .albumArtId(artist.getAlbumArtId())
                    .clickListener(new ArtistClickListener(artist.getId()))
                    .addTo(this);
        });
    }

    public static class Data {
        public String title;
        public List<Artist> artistList;
    }
}
