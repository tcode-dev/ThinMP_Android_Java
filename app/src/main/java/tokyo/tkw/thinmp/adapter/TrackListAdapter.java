package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.TitleViewHolder;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class TrackListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int VIEW_TYPE_TITLE = 1;
    private final static int VIEW_TYPE_TRACK = 2;
    private OnTrackListItemClickListener mListener;
    private int mItemCount;

    private ArrayList<Track> mTrackList;

    public TrackListAdapter(@NonNull ArrayList<Track> trackList,
                            OnTrackListItemClickListener listener) {
        mTrackList = trackList;
        mListener = listener;
        mItemCount = mTrackList.size() + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TITLE: return createTitleViewHolder(parent, viewType);
            case VIEW_TYPE_TRACK: return createTrackViewHolder(parent, viewType);
            default: throw new RuntimeException();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_TRACK: bindTrackViewHolder(holder, position--);
            default: break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_TITLE: VIEW_TYPE_TRACK;
    }

    private TitleViewHolder createTitleViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title, parent,
                false);

        return new TitleViewHolder(view);
    }

    private TrackViewHolder createTrackViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row, parent,
                false);

        return new TrackViewHolder(view);
    }

    private void bindTrackViewHolder(RecyclerView.ViewHolder holder, int position) {
        TrackViewHolder trackViewHolder = (TrackViewHolder) holder;
        Track track = mTrackList.get(position);
        String title = track.getTitle();

        GlideUtil.bitmap(track.getThumbnailId(), trackViewHolder.thumbnail);
        trackViewHolder.track.setText(title);
        trackViewHolder.artist.setText(track.getArtistName());

        trackViewHolder.itemView.setOnClickListener(listItemClickListener(position));
        trackViewHolder.menu.setOnClickListener(openMenuButtonClickListener(track));
    }

    /**
     * listitemをクリックしたときのイベント
     *
     * @param position
     * @return
     */
    private View.OnClickListener listItemClickListener(int position) {
        return view -> mListener.onClickItem(position);
    }

    /**
     * メニューオープンのイベント
     *
     * @return
     */
    public View.OnClickListener openMenuButtonClickListener(Track track) {
        return view -> mListener.onClickMenu(view, track);
    }

    /**
     * interface
     */
    public interface OnTrackListItemClickListener {
        void onClickItem(int position);

        void onClickMenu(View view, Track track);
    }
}