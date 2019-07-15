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
import tokyo.tkw.thinmp.viewHolder.ImageRowViewHolder;

public class PlaylistsEditAdapter extends RecyclerView.Adapter<ImageRowViewHolder> {
    private RealmList<PlaylistRealm> realmList;
    private Map<Integer, Playlist> playlistMap;

    public PlaylistsEditAdapter(RealmList<PlaylistRealm> realmList,
                                Map<Integer, Playlist> playlistMap) {
        this.realmList = realmList;
        this.playlistMap = playlistMap;
    }

    @Override
    public ImageRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item,
                parent, false);

        return new ImageRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageRowViewHolder holder, int position) {
        PlaylistRealm playlistRealm = realmList.get(position);
        int playlistId = playlistRealm.getId();
        Playlist playlist = playlistMap.get(playlistId);
        String name = playlist.getName();

        GlideUtil.bitmap(playlist.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(name);

        holder.itemView.setOnClickListener(new PlaylistClickListener(playlistId));
    }

    @Override
    public int getItemCount() {
        return realmList.size();
    }
}
