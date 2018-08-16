package tokyo.tkw.thinmp.favorite;

import java.util.ArrayList;

import io.realm.RealmResults;
import tokyo.tkw.thinmp.model.Track;
import tokyo.tkw.thinmp.music.MusicList;

public class FavoriteList {
    public static ArrayList<Track> getTrackList(RealmResults<Favorite> favoriteList) {
        ArrayList<Track> trackList = new ArrayList<Track>();

        for (Favorite favorite: favoriteList) {
            Track track = MusicList.getTrack(favorite.getTrackId());
            trackList.add(track);
        }

        return trackList;
    }
}
