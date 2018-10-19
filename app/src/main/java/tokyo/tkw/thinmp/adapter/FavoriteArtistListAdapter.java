package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteArtist;
import tokyo.tkw.thinmp.listener.ArtistClickListener;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;
import tokyo.tkw.thinmp.viewHolder.ArtistViewHolder;

public class FavoriteArtistListAdapter extends RealmRecyclerViewAdapter<FavoriteArtist, ArtistViewHolder> {
    private Activity mContext;
    private ThumbnailProvider mThumbnailProvider;

    public FavoriteArtistListAdapter(@NonNull Activity context, OrderedRealmCollection<FavoriteArtist> favoriteList) {
        super(favoriteList, true);

        mContext = context;
        mThumbnailProvider = new ThumbnailProvider();
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        final FavoriteArtist favorite = getItem(position);
        final Artist artist = getArtist(favorite.getArtistId());

        holder.thumbnail.setImageBitmap(getThumbnail(artist.getThumbnailId()));
        holder.artist.setText(artist.getName());

        holder.itemView.setOnClickListener(new ArtistClickListener(mContext, artist.getId()));
    }

    private Artist getArtist(String id) {
        return MusicList.getArtist(id);
    }

    private Bitmap getThumbnail(String id) {
        return mThumbnailProvider.getThumbnail(id);
    }
}
