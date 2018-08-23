package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tokyo.tkw.thinmp.listener.ArtistClickListener;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.ThumbnailController;
import tokyo.tkw.thinmp.viewHolder.ArtistViewHolder;

/**
 * Created by tk on 2018/03/22.
 */

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistViewHolder> {
    private Activity mContext;
    private ThumbnailController mThumbnailController;

    private ArrayList<Artist> mArtistList;

    public ArtistListAdapter(@NonNull Activity context, @NonNull ArrayList<Artist> artistList) {
        mContext = context;
        mThumbnailController = new ThumbnailController(context);
        mArtistList = artistList;
    }
    @Override
    public ArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);

        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistViewHolder holder, int position) {
        Artist artist = mArtistList.get(position);

        holder.thumbnail.setImageBitmap(mThumbnailController.getThumbnail(artist.getThumbnailId()));
        holder.artist.setText(artist.getName());

        holder.itemView.setOnClickListener(new ArtistClickListener(mContext, artist.getId()));
    }

    @Override
    public int getItemCount() {
        return mArtistList.size();
    }
}
