package tokyo.tkw.thinmp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteArtist;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;
import tokyo.tkw.thinmp.viewHolder.ArtistViewHolder;

public class FavoriteArtistEditAdapter extends RecyclerView.Adapter<ArtistViewHolder> {
    private List<FavoriteArtist> mFavoriteList;
    private ThumbnailProvider mThumbnailProvider;

    public FavoriteArtistEditAdapter(List<FavoriteArtist> favoriteList) {
        mFavoriteList = favoriteList;
        mThumbnailProvider = new ThumbnailProvider();
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        final FavoriteArtist favorite = mFavoriteList.get(position);
        final Artist artist = getArtist(favorite.getArtistId());
        String title = artist.getName();

        holder.thumbnail.setImageBitmap(mThumbnailProvider.getThumbnail(artist.getThumbnailId()));
        holder.artist.setText(title);
    }

    @Override
    public int getItemCount() {
        return mFavoriteList.size();
    }

    private Artist getArtist(String id) {
        return MusicList.getArtist(id);
    }
}
