package tokyo.tkw.thinmp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

/**
 * TrackListAdapter
 */
public class TrackListAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private ThumbnailProvider mThumbnailProvider;
    private OnTrackListItemClickListener mListener;
    private int mItemCount;

    private ArrayList<Track> mTrackList;

    public TrackListAdapter(@NonNull ArrayList<Track> trackList, OnTrackListItemClickListener listener) {
        mThumbnailProvider = new ThumbnailProvider();
        mTrackList = trackList;
        mListener = listener;
        mItemCount = mTrackList.size();
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row, parent, false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        Track track = mTrackList.get(position);
        String title = track.getTitle();

        holder.thumbnail.setImageBitmap(mThumbnailProvider.getThumbnail(track.getThumbnailId()));
        holder.track.setText(title);
        holder.artist.setText(track.getArtistName());

        holder.itemView.setOnClickListener(listItemClickListener(position));
        holder.menu.setOnClickListener(openMenuButtonClickListener(track));
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    /**
     * listitemをクリックしたときのイベント
     * @param position
     * @return
     */
    private View.OnClickListener listItemClickListener(int position) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mListener.onClickItem(position);
            }
        };
    }

    /**
     * メニューオープンのイベント
     * @return
     */
    public View.OnClickListener openMenuButtonClickListener(Track track) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickMenu(view, track);
            }
        };
    }

    /**
     * interface
     */
    public interface OnTrackListItemClickListener {
        void onClickItem(int position);
        void onClickMenu(View view, Track track);
    }
}