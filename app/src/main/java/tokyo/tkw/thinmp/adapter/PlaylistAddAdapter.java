package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ImageRowViewHolder;

public class PlaylistAddAdapter extends RealmRecyclerViewAdapter<PlaylistRealm,
        ImageRowViewHolder> {
    private Track mTrack;
    private Runnable mCallback;

    public PlaylistAddAdapter(OrderedRealmCollection<PlaylistRealm> playlists, Track track,
                              Runnable callback) {
        super(playlists, true);

        mTrack = track;
        mCallback = callback;
    }

    @Override
    public ImageRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item,
                parent, false);

        return new ImageRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageRowViewHolder holder, int position) {
        PlaylistRealm playlistRealm = getItem(position);
        int playlistId = playlistRealm.getId();
        PlaylistTrackRealm playlistTrackRealm = playlistRealm.getTrackRealmList().first();

        GlideUtil.bitmap(playlistTrackRealm.getAlbumArtId(), holder.albumArt);
        holder.primaryText.setText(playlistRealm.getName());

        holder.itemView.setOnClickListener(v -> {
            PlaylistRegister playlistRegister = new PlaylistRegister();
            playlistRegister.add(playlistId, mTrack);
            mCallback.run();
        });
    }
}
