package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import io.realm.RealmList;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.ITrackClickListener;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class PlaylistDetailAdapter extends RealmRecyclerViewAdapter<PlaylistTrackRealm,
        TrackViewHolder> {
    private Map<String, Track> mTrackMap;
    private ITrackClickListener mListener;
    private RecyclerView mRecycler;

    public PlaylistDetailAdapter(RealmList<PlaylistTrackRealm> playlistTrackRealms,
                                 Map<String, Track> trackMap, ITrackClickListener listener) {
        super(playlistTrackRealms, true);

        mTrackMap = trackMap;
        mListener = listener;
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

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row, parent,
                false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        PlaylistTrackRealm playlistTrackRealm = getItem(position);

        Track track = mTrackMap.get(playlistTrackRealm.getTrackId());

        GlideUtil.bitmap(track.getAlbumArtId(), holder.albumArt);
        holder.track.setText(track.getTitle());
        holder.artist.setText(track.getArtistName());

        holder.itemView.setOnClickListener(createClickTrackListener());
        holder.menu.setOnClickListener(createClickMenuListener());
    }

    private View.OnClickListener createClickTrackListener() {
        return view -> {
            mListener.onClickTrack(view, mRecycler.getChildAdapterPosition(view));
        };
    }

    private View.OnClickListener createClickMenuListener() {
        return view -> {
            mListener.onClickMenu(view, mRecycler.getChildAdapterPosition((View) view.getParent()));
        };
    }
}

