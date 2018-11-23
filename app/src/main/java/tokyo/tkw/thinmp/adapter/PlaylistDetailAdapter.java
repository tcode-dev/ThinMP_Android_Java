package tokyo.tkw.thinmp.adapter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class PlaylistDetailAdapter extends RealmRecyclerViewAdapter<PlaylistTrack, TrackViewHolder> {
    private ThumbnailProvider mThumbnailProvider;

    public PlaylistDetailAdapter(OrderedRealmCollection<PlaylistTrack> playlistTracks) {
        super(playlistTracks, true);

        mThumbnailProvider = new ThumbnailProvider();
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row, parent, false);

        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        PlaylistTrack playlistTrack = getItem(position);
        System.out.println(playlistTrack);
        Track track = getTrack(playlistTrack.getTrackId());

        String title = track.getTitle();

        holder.thumbnail.setImageBitmap(getThumbnail(track.getThumbnailId()));
        holder.track.setText(title);
        holder.artist.setText(track.getArtistName());
    }

    private Track getTrack(String id) {
        return MusicList.getTrack(id);
    }

    private Bitmap getThumbnail(String id) {
        return mThumbnailProvider.getThumbnail(id);
    }
}

