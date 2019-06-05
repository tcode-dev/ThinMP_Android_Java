package tokyo.tkw.thinmp.epoxy.controller;

import android.content.Context;

import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.Typed2EpoxyController;

import java.util.List;

import tokyo.tkw.thinmp.epoxy.model.ArtistListItemModel_;
import tokyo.tkw.thinmp.epoxy.model.HeaderModel_;
import tokyo.tkw.thinmp.listener.ArtistClickListener;
import tokyo.tkw.thinmp.music.Artist;

public class ArtistsController extends Typed2EpoxyController<ArtistsController.Data, Context> {
    @AutoModel HeaderModel_ headerModel;
    @AutoModel ArtistListItemModel_ artistListItemModel;

    @Override
    protected void buildModels(Data data, Context context) {
        new HeaderModel_().id("header").title(data.title).addTo(this);

        for (Artist artist : data.artistList) {
            new ArtistListItemModel_()
                    .id(artist.getId())
                    .artistName(artist.getName())
                    .thumbnailIdList(artist.getThumbnailIdList())
                    .clickListener(new ArtistClickListener(context, artist.getId()))
                    .addTo(this);
        }
    }

    public static class Data {
        public String title;
        public List<Artist> artistList;
    }
}
