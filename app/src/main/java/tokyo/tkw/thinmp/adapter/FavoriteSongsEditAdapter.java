package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class FavoriteSongsEditAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private List<FavoriteSongRealm> mFavoriteList;
    private Map<String, Track> mTrackMap;

    public FavoriteSongsEditAdapter(List<FavoriteSongRealm> favoriteList, Map<String, Track> trackMap) {
        mFavoriteList = favoriteList;
        mTrackMap = trackMap;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row_track, parent,
                false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        FavoriteSongRealm favorite = mFavoriteList.get(position);
        Track track = mTrackMap.get(favorite.getTrackId());
        String title = track.getTitle();

        GlideUtil.bitmap(track.getAlbumArtId(), holder.albumArt);
        holder.track.setText(title);
        holder.artist.setText(track.getArtistName());
    }

    @Override
    public int getItemCount() {
        return mFavoriteList.size();
    }
}
