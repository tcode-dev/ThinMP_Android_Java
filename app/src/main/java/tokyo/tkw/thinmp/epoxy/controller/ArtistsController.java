package tokyo.tkw.thinmp.epoxy.controller;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.TypedEpoxyController;

import java.util.ArrayList;

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

        for (Artist artist : data.artistList) {
            new ArtistListItemModel_()
                    .id(artist.getId())
                    .artistName(artist.getName())
                    .albumArtIdList(artist.getAlbumArtIdList())
                    .clickListener(new ArtistClickListener(artist.getId()))
                    .addTo(this);
        }
    }

    public static class Data {
        public String title;
        public ArrayList<Artist> artistList;
    }
}
