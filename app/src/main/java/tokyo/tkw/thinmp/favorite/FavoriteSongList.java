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
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;

public class FavoriteSongList {
    private Context mContext;
    private RealmResults<FavoriteSongRealm> mRealmResults;

    public FavoriteSongList(Context context) {
        mContext = context;
        mRealmResults = findAll();
    }

    public RealmResults<FavoriteSongRealm> getRealmResults() {
        return mRealmResults;
    }

    public ArrayList<FavoriteSongRealm> getList() {
        return (ArrayList<FavoriteSongRealm>) Stream.of(mRealmResults).toList();
    }

    public ArrayList<Track> getTrackList() {
        ArrayList<String> trackIdList = (ArrayList<String>) Stream.of(mRealmResults)
                .map(FavoriteSongRealm::getTrackId)
                .collect(Collectors.toList());

        TracksContentProvider provider = new TracksContentProvider(mContext, trackIdList);

        ArrayList<Track> trackList = provider.getList();

        Map<String, Track> trackMap = toTrackMap(trackList);

        return (ArrayList<Track>) Stream.of(trackIdList)
                .map(trackMap::get)
                .collect(Collectors.toList());
    }

    public Map<String, Track> getTrackMap() {
        return toTrackMap(getTrackList());
    }

    private Map<String, Track> toTrackMap(ArrayList<Track> trackList) {
        return Stream.of(trackList).collect(Collectors.toMap(Track::getId, track -> track));
    }

    private RealmResults<FavoriteSongRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteSongRealm.class).findAll().sort(FavoriteSongRealm.ID);
    }
}
