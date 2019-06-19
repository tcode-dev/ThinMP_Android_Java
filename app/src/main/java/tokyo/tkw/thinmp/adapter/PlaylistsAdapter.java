package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.PlaylistClickListener;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ImageRowViewHolder;

public class PlaylistsAdapter extends RealmRecyclerViewAdapter<PlaylistRealm, ImageRowViewHolder> {
    public PlaylistsAdapter(OrderedRealmCollection<PlaylistRealm> playlists) {
        super(playlists, true);
    }

    @Override
    public ImageRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item,
                parent, false);

        return new ImageRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageRowViewHolder holder, int position) {
        PlaylistRealm playlistRealm = getItem(position);
        int playlistId = playlistRealm.getId();
        String name = playlistRealm.getName();
        PlaylistTrackRealm playlistTrackRealm = playlistRealm.getTracks().first();

        GlideUtil.bitmap(playlistTrackRealm.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(name);

        // プレイリスト一覧画面の場合
        holder.itemView.setOnClickListener(new PlaylistClickListener(playlistId));
    }
}
