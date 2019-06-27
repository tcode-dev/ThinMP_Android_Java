package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.PlaylistClickListener;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ImageRowViewHolder;

public class PlaylistsEditAdapter extends RecyclerView.Adapter<ImageRowViewHolder> {
    private RealmList<PlaylistRealm> mList;

    public PlaylistsEditAdapter(RealmList<PlaylistRealm> list) {
        mList = list;
    }

    @Override
    public ImageRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item,
                parent, false);

        return new ImageRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageRowViewHolder holder, int position) {
        PlaylistRealm playlistRealm = mList.get(position);
        int playlistId = playlistRealm.getId();
        String name = playlistRealm.getName();
        PlaylistTrackRealm playlistTrackRealm = playlistRealm.getTracks().first();

        GlideUtil.bitmap(playlistTrackRealm.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(name);

        holder.itemView.setOnClickListener(new PlaylistClickListener(playlistId));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
