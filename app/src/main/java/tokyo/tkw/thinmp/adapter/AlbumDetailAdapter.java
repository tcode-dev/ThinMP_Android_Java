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
import tokyo.tkw.thinmp.viewHolder.AlbumTrackListViewHolder;

public class AlbumDetailAdapter extends RecyclerView.Adapter<AlbumTrackListViewHolder> {
    private List<Track> mTrackList;
    private ITrackClickListener mListener;
    private RecyclerView mRecycler;

    public AlbumDetailAdapter(@NonNull List<Track> trackList, ITrackClickListener listener) {
        mTrackList = trackList;
        mListener = listener;
    }

    @Override
    public AlbumTrackListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.album_track_list_item,
                        parent, false);

        return new AlbumTrackListViewHolder(view);
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
    public void onBindViewHolder(AlbumTrackListViewHolder holder, int position) {
        Track track = mTrackList.get(position);
        String title = track.getTitle();

        holder.title.setText(title);
        holder.itemView.setOnClickListener(onClickTrack());
        holder.menu.setOnClickListener(onClickMenu());
    }

    @Override
    public int getItemCount() {
        return mTrackList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    private View.OnClickListener onClickTrack() {
        return view -> {
            mListener.onClickTrack(view, mRecycler.getChildAdapterPosition(view));
        };
    }

    private View.OnClickListener onClickMenu() {
        return view -> {
            mListener.onClickMenu(view, mRecycler.getChildAdapterPosition((View) view.getParent()));
        };
    }
}
