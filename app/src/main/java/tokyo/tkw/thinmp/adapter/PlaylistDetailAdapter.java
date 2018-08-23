package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.TrackClickListener;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;
import tokyo.tkw.thinmp.util.ThumbnailController;
import tokyo.tkw.thinmp.viewHolder.TrackViewHolder;

public class PlaylistDetailAdapter extends RealmRecyclerViewAdapter<PlaylistTrack, TrackViewHolder> {
    private Activity mContext;
    private ThumbnailController mThumbnailController;
    private OrderedRealmCollection<PlaylistTrack> mPlaylistTracks;
    private ArrayList<Track> mTrackList;

    public PlaylistDetailAdapter(Activity context, OrderedRealmCollection<PlaylistTrack> playlistTracks, ArrayList<Track> trackList) {
        super(playlistTracks, true);

        mContext = context;
        mPlaylistTracks = playlistTracks;
        mTrackList = trackList;
        mThumbnailController = new ThumbnailController(context);
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

        holder.itemView.setOnClickListener(new TrackClickListener(mContext, mTrackList, position));
//        holder.menu.setOnClickListener(new OpenTrackMenuClickListener(mContext, track.getId(), title));
    }

    private Track getTrack(String id) {
        return MusicList.getTrack(id);
    }

    private Bitmap getThumbnail(String id) {
        return mThumbnailController.getThumbnail(id);
    }
}

