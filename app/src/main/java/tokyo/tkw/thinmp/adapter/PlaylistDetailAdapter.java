package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class PlaylistDetailAdapter extends RealmRecyclerViewAdapter<PlaylistTrackRealm,
        TrackViewHolder> {
    private Map<String, Track> mTrackMap;
    public PlaylistDetailAdapter(OrderedRealmCollection<PlaylistTrackRealm> playlistTrackRealms,
                                 Map<String, Track> trackMap) {
        super(playlistTrackRealms, true);

        mTrackMap = trackMap;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row, parent,
                false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        PlaylistTrackRealm playlistTrackRealm = getItem(position);

        Track track = mTrackMap.get(playlistTrackRealm.getTrackId());
        String title = track.getTitle();

        GlideUtil.bitmap(track.getAlbumArtId(), holder.albumArt);
        holder.track.setText(title);
        holder.artist.setText(track.getArtistName());
    }
}

