package tokyo.tkw.thinmp.listener;

import android.content.Context;

import java.util.List;

import tokyo.tkw.thinmp.favorite.FavoriteSongs;
import tokyo.tkw.thinmp.music.Track;

public class FavoriteSongClickListener extends BaseTrackClickListener {
    @Override
    public List<Track> getTrackList(Context context) {
        FavoriteSongs favoriteSongs = new FavoriteSongs(context);

        return favoriteSongs.getSortedTrackList();
    }
}
