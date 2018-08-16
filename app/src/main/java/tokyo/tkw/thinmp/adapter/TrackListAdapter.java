package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.ItemClickListener;
import tokyo.tkw.thinmp.util.ThumbnailController;
import tokyo.tkw.thinmp.model.Track;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

/**
 * Created by tk on 2018/03/22.
 */

public class TrackListAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private Activity mContext;
    private ThumbnailController mThumbnailController;

    private ArrayList<Track> mTrackList;

    public TrackListAdapter(@NonNull Activity context, @NonNull ArrayList<Track> trackList) {
        mContext = context;
        mThumbnailController = new ThumbnailController(context);
        mTrackList = trackList;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row, parent, false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        Track track = mTrackList.get(position);

        holder.thumbnail.setImageBitmap(getThumbnail(track.getThumbnailId()));
        holder.track.setText(track.getTitle());
        holder.artist.setText(track.getArtistName());
        holder.itemView.setOnClickListener(new ItemClickListener(mContext, mTrackList, position));
//        holder.mPopupMenu.setOnClickListener(new AlbumTrackListAdapter.ViewOnClickListener(track.getId()));
    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    private Bitmap getThumbnail(String id) {
        return mThumbnailController.getThumbnail(id);
    }
}
