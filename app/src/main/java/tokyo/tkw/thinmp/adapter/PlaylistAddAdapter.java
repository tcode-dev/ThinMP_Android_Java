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
import tokyo.tkw.thinmp.viewHolder.ImageRowViewHolder;

public class PlaylistAddAdapter extends RealmRecyclerViewAdapter<PlaylistRealm,
        ImageRowViewHolder> {
    private Map<String, Playlist> playlistMap;
    private Track track;
    private Runnable callback;

    public PlaylistAddAdapter(OrderedRealmCollection<PlaylistRealm> playlists, Map<String,
            Playlist> playlistMap, Track track, Runnable callback) {
        super(playlists, true);

        this.playlistMap = playlistMap;
        this.track = track;
        this.callback = callback;
    }

    @Override
    public ImageRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row_playlist,
                parent, false);

        return new ImageRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageRowViewHolder holder, int position) {
        PlaylistRealm playlistRealm = getItem(position);
        String playlistId = playlistRealm.getId();
        Playlist playlist = playlistMap.get(playlistId);

        GlideUtil.bitmap(playlist.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(playlistRealm.getName());

        holder.itemView.setOnClickListener(v -> {
            PlaylistRegister playlistRegister = new PlaylistRegister();
            playlistRegister.add(playlistId, track);
            callback.run();
        });
    }
}
