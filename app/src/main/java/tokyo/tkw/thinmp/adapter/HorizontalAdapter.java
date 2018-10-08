package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.AlbumClickListener;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.util.ThumbnailController;
import tokyo.tkw.thinmp.viewHolder.HorizontalViewHolder;
import tokyo.tkw.thinmp.viewHolder.TopViewHolder;

/**
 * スライドのテスト
 */
public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalViewHolder> {
    private Activity mContext;
    private ThumbnailController mThumbnailController;
    private ArrayList<Album> mAlbumList;

    public HorizontalAdapter(@NonNull Activity context, @NonNull ArrayList<Album> albumList) {
        mContext = context;
        mThumbnailController = new ThumbnailController(context);
        mAlbumList = albumList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);

        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder holder, int position) {
        Album album = mAlbumList.get(position);

        holder.thumbnail.setImageBitmap(mThumbnailController.getThumbnail(album.getThumbnailId()));
        holder.albumName.setText(album.getName());
        holder.artistName.setText(album.getArtistName());

        holder.itemView.setOnClickListener(new AlbumClickListener(mContext, album.getId()));
    }

    @Override
    public int getItemCount() {
        return mAlbumList.size();
    }
}
