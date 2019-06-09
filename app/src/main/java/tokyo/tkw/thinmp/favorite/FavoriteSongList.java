package tokyo.tkw.thinmp.favorite;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.TracksContentProvider;

public class FavoriteSongList {
    private Context mContext;
    private RealmResults<FavoriteSong> mRealmResults;

    public FavoriteSongList(Context context) {
        mContext = context;
        mRealmResults = findAll();
    }

    public RealmResults<FavoriteSong> getRealmResults() {
        return mRealmResults;
    }

    public ArrayList<FavoriteSong> getList() {
        return (ArrayList<FavoriteSong>) Stream.of(mRealmResults).toList();
    }

    public ArrayList<Track> getTrackList() {
        ArrayList<String> trackIdList =
                (ArrayList<String>) Stream.of(mRealmResults).map(FavoriteSong::getTrackId).collect(Collectors.toList());

        TracksContentProvider tracksContentProvider = new TracksContentProvider(mContext,
                trackIdList);

        return tracksContentProvider.getList();
    }

    public Map<String, Track> getTrackMap() {
        return getTrackMap(getTrackList());
    }

    public Map<String, Track> getTrackMap(ArrayList<Track> trackList) {
        return Stream.of(trackList).collect(Collectors.toMap(track -> track.getId(),
                track -> track));
    }

    private RealmResults<FavoriteSong> findAll() {
        Realm.init(mContext);
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteSong.class).findAll().sort("id");
    }
}
