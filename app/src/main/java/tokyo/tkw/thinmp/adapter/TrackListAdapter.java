package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.ITrackClickListener;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.TitleViewHolder;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class TrackListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int VIEW_TYPE_TITLE = 1;
    private final static int VIEW_TYPE_TRACK = 2;
    private final static int HEADER_COUNT = 1;
    private List<Track> mTrackList;
    private ITrackClickListener mListener;
    private int mItemCount;
    private RecyclerView mRecycler;

    public TrackListAdapter(@NonNull List<Track> trackList, ITrackClickListener listener) {
        mTrackList = trackList;
        mListener = listener;
        mItemCount = mTrackList.size() + HEADER_COUNT;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecycler = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        mRecycler = null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_TITLE:
                return createTitleViewHolder(parent);
            case VIEW_TYPE_TRACK:
                return createTrackViewHolder(parent);
            default:
                throw new RuntimeException();
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case VIEW_TYPE_TRACK:
                bindTrackViewHolder(holder, position - HEADER_COUNT);
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_TITLE : VIEW_TYPE_TRACK;
    }

    private TitleViewHolder createTitleViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.title, parent,
                false);

        return new TitleViewHolder(view);
    }

    private TrackViewHolder createTrackViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row, parent,
                false);

        return new TrackViewHolder(view);
    }

    private void bindTrackViewHolder(RecyclerView.ViewHolder holder, int position) {
        int trackPosition = position;
        TrackViewHolder trackViewHolder = (TrackViewHolder) holder;
        Track track = mTrackList.get(trackPosition);
        String title = track.getTitle();

        GlideUtil.bitmap(track.getAlbumArtId(), trackViewHolder.albumArt);
        trackViewHolder.track.setText(title);
        trackViewHolder.artist.setText(track.getArtistName());

        trackViewHolder.itemView.setOnClickListener(onClickTrack());
        trackViewHolder.menu.setOnClickListener(onClickMenu());
    }

    private int getTrackListInPosition(View view) {
        return mRecycler.getChildAdapterPosition(view) - HEADER_COUNT;
    }

    private View.OnClickListener onClickTrack() {
        return view -> {
            mListener.onClickTrack(view, getTrackListInPosition(view));
        };
    }

    private View.OnClickListener onClickMenu() {
        return view -> {
            mListener.onClickMenu(view, getTrackListInPosition((View) view.getParent()));
        };
    }
}