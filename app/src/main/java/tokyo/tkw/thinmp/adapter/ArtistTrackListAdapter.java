package tokyo.tkw.thinmp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.activity.ArtistActivity;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class ArtistTrackListAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private OnTrackListItemClickListener mListener;
    private ArtistActivity.ArtistActivityListener mArtistActivityListener;
    private int mItemCount;

    private ArrayList<Track> mTrackList;

    public ArtistTrackListAdapter(@NonNull ArrayList<Track> trackList,
                                  OnTrackListItemClickListener listener,
                                  ArtistActivity.ArtistActivityListener artistActivityListener) {
        mTrackList = trackList;
        mListener = listener;
        mArtistActivityListener = artistActivityListener;
        mItemCount = mTrackList.size();
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_track_list_item, parent,
                false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        Track track = mTrackList.get(position);
        String title = track.getTitle();

        holder.thumbnail.setImageBitmap(mArtistActivityListener.getThumbnail(track.getThumbnailId()));
        holder.track.setText(title);

        holder.itemView.setOnClickListener(listItemClickListener(position));
        holder.menu.setOnClickListener(openMenuButtonClickListener(track));
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    /**
     * listitemをクリックしたときのイベント
     *
     * @param position
     * @return
     */
    private View.OnClickListener listItemClickListener(int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onClickItem(position);
            }
        };
    }

    /**
     * メニューオープンのイベント
     *
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