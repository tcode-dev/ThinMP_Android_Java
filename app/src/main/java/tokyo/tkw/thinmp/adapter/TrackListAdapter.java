package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tokyo.tkw.thinmp.menu.TrackMenu;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.ThumbnailController;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

/**
 * TrackListAdapter
 */
public class TrackListAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private Activity mContext;
    private ThumbnailController mThumbnailController;
    private OnTrackListItemClickListener mListener;
    private int mItemCount;

    private ArrayList<Track> mTrackList;

    public TrackListAdapter(@NonNull Activity context, @NonNull ArrayList<Track> trackList, OnTrackListItemClickListener listener) {
        mContext = context;
        mThumbnailController = new ThumbnailController(context);
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

        holder.thumbnail.setImageBitmap(mThumbnailController.getThumbnail(track.getThumbnailId()));
        holder.track.setText(title);
        holder.artist.setText(track.getArtistName());

        holder.itemView.setOnClickListener(listItemClickListener(position));
        holder.menu.setOnClickListener(openMenuButtonClickListener(track.getId(), title));
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
                mListener.OnClickItem(position);
            }
        };
    }

    /**
     * メニューオープンのイベント
     * @return
     */
    public View.OnClickListener openMenuButtonClickListener(String trackId, String title) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackMenu trackMenu = new TrackMenu(mContext, v, trackId, title);
                trackMenu.show();
            }
        };
    }

    /**
     * interface
     */
    public interface OnTrackListItemClickListener {
        void OnClickItem(int position);
    }
}