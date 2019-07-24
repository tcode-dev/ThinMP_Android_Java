package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class PlaylistDetailEditAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private List<Track> trackList;

    public PlaylistDetailEditAdapter(List<Track> trackList) {
        this.trackList = trackList;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_linear_track, parent, false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        Track track = trackList.get(position);

        GlideUtil.bitmap(track.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(track.getName());
        holder.secondaryText.setText(track.getArtistName());
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }
}

