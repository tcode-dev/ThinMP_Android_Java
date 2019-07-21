package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class PlaylistDetailEditAdapter extends RecyclerView.Adapter<TrackViewHolder> {
    private RealmList<PlaylistTrackRealm> trackRealmList;
    private Map<String, Track> trackMap;

    public PlaylistDetailEditAdapter(RealmList<PlaylistTrackRealm> trackRealmList,
                                     Map<String, Track> trackMap) {
        this.trackRealmList = trackRealmList;
        this.trackMap = trackMap;
    }

    @NonNull
    @Override
    public TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_linear_track, parent,
                false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackViewHolder holder, int position) {
        PlaylistTrackRealm realm = trackRealmList.get(position);
        Track track = trackMap.get(realm.getTrackId());

        GlideUtil.bitmap(track.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(track.getName());
        holder.secondaryText.setText(track.getArtistName());
    }

    @Override
    public int getItemCount() {
        return trackRealmList.size();
    }
}

