package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.annimon.stream.Optional;

import java.util.Map;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.PlaylistClickListener;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ImageRowViewHolder;

public class PlaylistsAdapter extends RealmRecyclerViewAdapter<PlaylistRealm, ImageRowViewHolder> {
    private Map<Integer, Optional<String>> albumArtMap;

    public PlaylistsAdapter(OrderedRealmCollection<PlaylistRealm> playlists,
                            Map<Integer, Optional<String>> albumArtMap) {
        super(playlists, true);

        this.albumArtMap = albumArtMap;
    }

    @NonNull
    @Override
    public ImageRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.panel, parent, false);

        return new ImageRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageRowViewHolder holder, int position) {
        PlaylistRealm playlistRealm = getItem(position);
        int playlistId = playlistRealm.getId();
        String name = playlistRealm.getName();

        GlideUtil.bitmap(albumArtMap.get(playlistId), holder.albumArt);
        holder.primaryText.setText(name);

        holder.itemView.setOnClickListener(new PlaylistClickListener(playlistId));
    }
}
