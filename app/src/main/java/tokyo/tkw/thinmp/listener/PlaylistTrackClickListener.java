package tokyo.tkw.thinmp.listener;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.playlist.PlaylistTrack;

public class PlaylistTrackClickListener extends BaseTrackClickListener {
    private int mPlaylistId;

    public PlaylistTrackClickListener(int playlistId) {
        mPlaylistId = playlistId;
    }

    @Override
    public List<Track> getTrackList(Context context) {
        PlaylistTrack playlistTrack = new PlaylistTrack(context, mPlaylistId);

        return playlistTrack.getSortedTrackList();
    }
}
