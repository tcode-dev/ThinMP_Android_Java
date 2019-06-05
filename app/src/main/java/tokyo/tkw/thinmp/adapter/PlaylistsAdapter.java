package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.listener.PlaylistClickListener;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.playlist.PlaylistRegister;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ImageRowViewHolder;

public class PlaylistsAdapter extends RealmRecyclerViewAdapter<Playlist, ImageRowViewHolder> {
    private Activity mContext;
    private String mTrackId = null;
    private Runnable mCallback;

    public PlaylistsAdapter(Activity context, OrderedRealmCollection<Playlist> playlists) {
        super(playlists, true);

        mContext = context;
    }

    public PlaylistsAdapter(Activity context, OrderedRealmCollection<Playlist> playlists, String trackId, Runnable callback) {
        super(playlists, true);

        mContext = context;
        mTrackId = trackId;
        mCallback = callback;
    }

    @Override
    public ImageRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);

        return new ImageRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageRowViewHolder holder, int position) {
        Playlist playlist = getItem(position);
        int playlistId = playlist.getId();
        String name = playlist.getName();
        PlaylistTrack playlistTrack = playlist.getTracks().first();
        String trackId = playlistTrack.getTrackId();
        Track track = getTrack(trackId);

        GlideUtil.bitmap(track.getThumbnailId(), holder.thumbnail);
        holder.primaryText.setText(name);

        if (mTrackId != null) {
            // playlist追加画面の場合
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlaylistRegister playlistRegister = new PlaylistRegister();
                    Track track = MusicList.getTrack(mTrackId);
                    playlistRegister.add(playlistId, track);
                    mCallback.run();
                }
            });
        } else {
            // プレイリスト一覧画面の場合
            holder.itemView.setOnClickListener(new PlaylistClickListener(mContext, playlistId));
        }

    }

    private Track getTrack(String id) {
        return MusicList.getTrack(id);
    }
}
