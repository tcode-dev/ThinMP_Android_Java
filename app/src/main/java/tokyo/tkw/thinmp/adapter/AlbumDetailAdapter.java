package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.ITrackClickListener;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.viewHolder.AlbumTrackViewHolder;

public class AlbumDetailAdapter extends RecyclerView.Adapter<AlbumTrackViewHolder> {
    private List<Track> mTrackList;
    private ITrackClickListener mListener;
    private RecyclerView mRecycler;

    public AlbumDetailAdapter(@NonNull List<Track> trackList, ITrackClickListener listener) {
        mTrackList = trackList;
        mListener = listener;
    }

    @NonNull
    @Override
    public AlbumTrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_linear_album_track,
                        parent, false);

        return new AlbumTrackViewHolder(view);
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
    public void onBindViewHolder(AlbumTrackViewHolder holder, int position) {
        Track track = mTrackList.get(position);

        holder.primaryText.setText(track.getName());
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
