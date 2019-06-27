package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class PlaylistDetailEditAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private RealmList<PlaylistTrackRealm> mList;
    private Map<String, Track> mTrackMap;

    public PlaylistDetailEditAdapter(RealmList<PlaylistTrackRealm> list,
                                     Map<String, Track> trackMap) {
        mList = list;
        mTrackMap = trackMap;
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
        PlaylistTrackRealm realm = mList.get(position);
        Track track = mTrackMap.get(realm.getTrackId());
        GlideUtil.bitmap(track.getAlbumArtId(), holder.albumArt);
        holder.track.setText(track.getTitle());
        holder.artist.setText(track.getArtistName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

