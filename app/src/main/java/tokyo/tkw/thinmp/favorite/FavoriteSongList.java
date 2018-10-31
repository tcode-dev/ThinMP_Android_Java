package tokyo.tkw.thinmp.favorite;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.util.ActivityUtil;

public class FavoriteSongList {
    public static ArrayList<Track> getTrackList() {
        return getTrackList(getFavoriteList());
    }

    private static ArrayList<Track> getTrackList(RealmResults<FavoriteSong> favoriteList) {
        ArrayList<Track> trackList = new ArrayList<Track>();

        for (FavoriteSong favorite: favoriteList) {
            Track track = MusicList.getTrack(favorite.getTrackId());
            trackList.add(track);
        }

        return trackList;
    }

    public static RealmResults<FavoriteSong> getFavoriteList() {
        Realm.init(ActivityUtil.getContext());
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteSong.class).findAll().sort("id");
    }
}
