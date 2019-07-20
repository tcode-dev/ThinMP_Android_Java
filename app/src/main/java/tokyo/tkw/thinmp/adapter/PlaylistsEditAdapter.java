package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.PlaylistClickListener;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.PlaylistViewHolder;

public class PlaylistsEditAdapter extends RecyclerView.Adapter<PlaylistViewHolder> {
    private RealmList<PlaylistRealm> realmList;
    private Map<String, Playlist> playlistMap;

    public PlaylistsEditAdapter(RealmList<PlaylistRealm> realmList,
                                Map<String, Playlist> playlistMap) {
        this.realmList = realmList;
        this.playlistMap = playlistMap;
    }

    @Override
    public PlaylistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_linear_playlist,
                parent, false);

        return new PlaylistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaylistViewHolder holder, int position) {
        PlaylistRealm playlistRealm = realmList.get(position);
        String playlistId = playlistRealm.getId();
        Playlist playlist = playlistMap.get(playlistId);

        GlideUtil.bitmap(playlist.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(playlist.getName());
        holder.itemView.setOnClickListener(new PlaylistClickListener(playlistId));
    }

    @Override
    public int getItemCount() {
        return realmList.size();
    }
}
