package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.PlaylistViewHolder;

public class PlaylistAddAdapter extends RealmRecyclerViewAdapter<PlaylistRealm,
        PlaylistViewHolder> {
    private Map<String, Playlist> playlistMap;
    private PlaylistClickListener playlistClickListener;

    public PlaylistAddAdapter(OrderedRealmCollection<PlaylistRealm> playlists, Map<String,
            Playlist> playlistMap, PlaylistClickListener playlistClickListener) {
        super(playlists, true);

        this.playlistMap = playlistMap;
        this.playlistClickListener = playlistClickListener;
    }

    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_linear_playlist,
                parent, false);

        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaylistViewHolder holder, int position) {
        PlaylistRealm playlistRealm = getItem(position);
        String playlistId = playlistRealm.getId();
        Playlist playlist = playlistMap.get(playlistId);

        GlideUtil.bitmap(playlist.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(playlistRealm.getName());
        holder.itemView.setOnClickListener(createPlaylistClickListener(playlistId));
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
