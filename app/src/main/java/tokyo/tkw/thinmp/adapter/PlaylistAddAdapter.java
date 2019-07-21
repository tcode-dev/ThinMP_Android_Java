package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ArtistViewHolder;
import tokyo.tkw.thinmp.viewHolder.PlaylistViewHolder;

public class PlaylistAddAdapter extends RecyclerView.Adapter<PlaylistViewHolder> {
    private List<Playlist> playlists;
    private PlaylistClickListener playlistClickListener;

    public PlaylistAddAdapter(List<Playlist> playlists, PlaylistClickListener playlistClickListener) {

        this.playlists = playlists;
        this.playlistClickListener = playlistClickListener;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_linear_playlist,
                parent, false);

        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = playlists.get(position);

        GlideUtil.bitmap(playlist.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(playlist.getName());
        holder.itemView.setOnClickListener(createPlaylistClickListener(playlist.getId()));
    }

    @Override
    public int getItemCount() {
        return playlists.size();
    }

    private View.OnClickListener createPlaylistClickListener(String playlistId) {
        return view -> {
            playlistClickListener.onClick(view, playlistId);
        };
    }

    /**
     * interface
     */
    public interface PlaylistClickListener {
        void onClick(View view, String playlistId);
    }
}
