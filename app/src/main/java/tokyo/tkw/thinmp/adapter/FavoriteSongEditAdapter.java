package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.favorite.FavoriteSong;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class FavoriteSongEditAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private List<FavoriteSong> mFavoriteList;

    public FavoriteSongEditAdapter(List<FavoriteSong> favoriteList) {
        mFavoriteList = favoriteList;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row, parent,
                false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        final FavoriteSong favorite = mFavoriteList.get(position);
        Track track = getTrack(favorite.getTrackId());
        String title = track.getTitle();

        GlideUtil.bitmap(track.getThumbnailId(), holder.thumbnail);
        holder.track.setText(title);
        holder.artist.setText(track.getArtistName());
    }

    @Override
    public int getItemCount() {
        return mFavoriteList.size();
    }

    private Track getTrack(String id) {
        return MusicList.getTrack(id);
    }
}
