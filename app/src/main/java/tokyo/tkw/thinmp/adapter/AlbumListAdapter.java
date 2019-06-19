package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.AlbumClickListener;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.AlbumListViewHolder;

public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListViewHolder> {
    private Activity mContext;
    private ArrayList<Album> mAlbumList;

    public AlbumListAdapter(@NonNull Activity context, @NonNull ArrayList<Album> albumList) {
        mContext = context;
        mAlbumList = albumList;
    }

    @Override
    public AlbumListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.panel2, parent,
                false);

        return new AlbumListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumListViewHolder holder, int position) {
        Album album = mAlbumList.get(position);

        GlideUtil.bitmap(album.getAlbumArtId(), holder.albumArt);
        holder.albumName.setText(album.getName());
        holder.artistName.setText(album.getArtistName());

        holder.itemView.setOnClickListener(new AlbumClickListener(album.getId()));
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }
}
