package tokyo.tkw.thinmp.favorite;

import android.app.Activity;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.music.MusicList;

public class FavoriteSongList {
    public static ArrayList<Track> getTrackList(Activity activity) {
        return getTrackList(getFavoriteList(activity));
    }

    private static ArrayList<Track> getTrackList(RealmResults<FavoriteSong> favoriteList) {
        ArrayList<Track> trackList = new ArrayList<Track>();

        for (FavoriteSong favorite: favoriteList) {
            Track track = MusicList.getTrack(favorite.getTrackId());
            trackList.add(track);
        }

        return trackList;
    }

    public static RealmResults<FavoriteSong> getFavoriteList(Activity activity) {
        Realm.init(activity);
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteSong.class).findAll().sort("createdAt");
    }
}
