package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.PlaylistClickListener;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.PlaylistViewHolder;

public class PlaylistsEditAdapter extends RecyclerView.Adapter<PlaylistViewHolder> {
    private List<Playlist> playlists;

    public PlaylistsEditAdapter(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_linear_edit_playlist, parent, false);

        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);

        GlideUtil.bitmap(playlist.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(playlist.getName());
        holder.itemView.setOnClickListener(new PlaylistClickListener(playlist.getId()));
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }
}
