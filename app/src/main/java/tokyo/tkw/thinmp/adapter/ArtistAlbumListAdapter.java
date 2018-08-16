package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tokyo.tkw.thinmp.listener.AlbumClickListener;
import tokyo.tkw.thinmp.model.Album;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.ThumbnailController;
import tokyo.tkw.thinmp.viewHolder.ArtistAlbumListViewHolder;

/**
 * Created by tk on 2018/03/22.
 */

public class ArtistAlbumListAdapter extends RecyclerView.Adapter<ArtistAlbumListViewHolder> {
    private Activity mContext;
    private ThumbnailController mThumbnailController;
    private ArrayList<Album> mAlbumList;

    public ArtistAlbumListAdapter(@NonNull Activity context, @NonNull ArrayList<Album> albumList) {
        mContext = context;
        mThumbnailController = new ThumbnailController(context);
        mAlbumList = albumList;
    }

    @Override
    public ArtistAlbumListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_album_list_item, parent, false);

        return new ArtistAlbumListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistAlbumListViewHolder holder, int position) {
        Album album = mAlbumList.get(position);

        holder.thumbnail.setImageBitmap(mThumbnailController.getThumbnail(album.getThumbnailId()));
        holder.albumName.setText(album.getName());

        holder.itemView.setOnClickListener(new AlbumClickListener(mContext, album.getId()));
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }
}
