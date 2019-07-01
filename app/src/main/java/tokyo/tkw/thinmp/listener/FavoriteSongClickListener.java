package tokyo.tkw.thinmp.listener;

import android.content.Context;

import java.util.ArrayList;

import tokyo.tkw.thinmp.favorite.FavoriteSongList;
import tokyo.tkw.thinmp.music.Track;

public class FavoriteSongClickListener extends BaseTrackClickListener {
    @Override
    public ArrayList<Track> getTrackList(Context context) {
        FavoriteSongList favoriteSongList = new FavoriteSongList(context);

        return favoriteSongList.getSortedTrackList();
    }
}
