package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ArtistViewHolder;

public class FavoriteArtistsEditAdapter extends RecyclerView.Adapter<ArtistViewHolder> {
    private List<FavoriteArtistRealm> mFavoriteList;
    private Map<String, Artist> mArtistMap;

    public FavoriteArtistsEditAdapter(List<FavoriteArtistRealm> favoriteList,
                                      Map<String, Artist> artistMap) {
        mFavoriteList = favoriteList;
        mArtistMap = artistMap;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_linear_artist,
                parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        FavoriteArtistRealm favorite = mFavoriteList.get(position);
        Artist artist = mArtistMap.get(favorite.getArtistId());

        GlideUtil.bitmap(artist.getAlbumArtId(), holder.albumArt, GlideUtil.ARTIST_RESOURCE_ID);
        holder.primaryText.setText(artist.getName());
    }

    @Override
    public int getItemCount() {
        return mFavoriteList.size();
    }
}
