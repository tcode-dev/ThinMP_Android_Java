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
    private Map<String, Track> trackMap;
    private ITrackClickListener trackClickListener;
    private RecyclerView recyclerView;

    public PlaylistDetailAdapter(RealmList<PlaylistTrackRealm> trackRealmList,
                                 Map<String, Track> trackMap,
                                 ITrackClickListener trackClickListener) {
        super(trackRealmList, true);

        this.trackMap = trackMap;
        this.trackClickListener = trackClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        this.recyclerView = null;
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

        Track track = trackMap.get(playlistTrackRealm.getTrackId());

        GlideUtil.bitmap(track.getAlbumArtId(), holder.albumArt);
        holder.track.setText(track.getTitle());
        holder.artist.setText(track.getArtistName());

        holder.itemView.setOnClickListener(createClickTrackListener());
        holder.menu.setOnClickListener(createClickMenuListener());
    }

    private View.OnClickListener createClickTrackListener() {
        return view -> {
            trackClickListener.onClickTrack(view, recyclerView.getChildAdapterPosition(view));
        };
    }

    private View.OnClickListener createClickMenuListener() {
        return view -> {
            trackClickListener.onClickMenu(view,
                    recyclerView.getChildAdapterPosition((View) view.getParent()));
        };
    }
}

