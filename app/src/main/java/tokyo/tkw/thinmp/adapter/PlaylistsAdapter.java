package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.Map;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.PlaylistClickListener;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.PlaylistViewHolder;

public class PlaylistsAdapter extends RealmRecyclerViewAdapter<PlaylistRealm, PlaylistViewHolder> {
    private Map<String, Playlist> playlistMap;

    public PlaylistsAdapter(OrderedRealmCollection<PlaylistRealm> playlists,
                            Map<String, Playlist> playlistMap) {
        super(playlists, true);

        this.playlistMap = playlistMap;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_grid_playlist, parent, false);

        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        PlaylistRealm playlistRealm = getItem(position);
        String playlistId = playlistRealm.getId();
        Playlist playlist = playlistMap.get(playlistId);

        GlideUtil.bitmap(playlist.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(playlist.getName());
        holder.itemView.setOnClickListener(new PlaylistClickListener(playlistId));
    }
}
