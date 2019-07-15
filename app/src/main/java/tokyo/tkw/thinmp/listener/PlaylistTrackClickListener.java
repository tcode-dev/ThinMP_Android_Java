package tokyo.tkw.thinmp.listener;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.playlist.Playlist;

public class PlaylistTrackClickListener extends BaseTrackClickListener {
    private int playlistId;

    public PlaylistTrackClickListener(int playlistId) {
        this.playlistId = playlistId;
    }

    @Override
    public List<Track> getTrackList(Context context) {
        Playlist playlist = Playlist.createInstance(context, playlistId);

        return playlist.getSortedTrackList();
    }
}
