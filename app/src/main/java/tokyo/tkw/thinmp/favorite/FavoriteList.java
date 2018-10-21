package tokyo.tkw.thinmp.favorite;

import android.app.Activity;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.music.MusicList;

public class FavoriteList {
    public static ArrayList<Track> getTrackList(Activity activity) {
        return getTrackList(getFavoriteList(activity));
    }

    private static ArrayList<Track> getTrackList(RealmResults<Favorite> favoriteList) {
        ArrayList<Track> trackList = new ArrayList<Track>();

        for (Favorite favorite: favoriteList) {
            Track track = MusicList.getTrack(favorite.getTrackId());
            trackList.add(track);
        }

        return trackList;
    }

    public static RealmResults<Favorite> getFavoriteList(Activity activity) {
        Realm.init(activity);
        Realm realm = Realm.getDefaultInstance();

        return realm.where(Favorite.class).findAll().sort("createdAt");
    }
}
