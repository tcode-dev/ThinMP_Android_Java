package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.realm.FavoriteArtistRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ArtistViewHolder;

public class FavoriteArtistEditAdapter extends RecyclerView.Adapter<ArtistViewHolder> {
    private ArrayList<FavoriteArtistRealm> mFavoriteList;
    private Map<String, Artist> mArtistMap;

    public FavoriteArtistEditAdapter(ArrayList<FavoriteArtistRealm> favoriteList,
                                     Map<String, Artist> artistMap) {
        mFavoriteList = favoriteList;
        mArtistMap = artistMap;
    }

    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_list_item,
                parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        FavoriteArtistRealm favorite = mFavoriteList.get(position);
        Artist artist = mArtistMap.get(favorite.getArtistId());
        String title = artist.getName();

        GlideUtil.bitmap(artist.getThumbnailIdList(), holder.thumbnail);
        holder.artistName.setText(title);
    }

    @Override
    public int getItemCount() {
        return mFavoriteList.size();
    }
}
