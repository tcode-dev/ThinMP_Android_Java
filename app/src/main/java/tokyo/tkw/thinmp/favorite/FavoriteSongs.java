package tokyo.tkw.thinmp.favorite;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.track.Track;
import tokyo.tkw.thinmp.provider.TrackContentProvider;
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;

public class FavoriteSongs {
    private Context mContext;
    private RealmResults<FavoriteSongRealm> mRealmResults;

    public FavoriteSongs(Context context) {
        mContext = context;
        mRealmResults = findAll();
    }
    public static FavoriteSongs createInstance(Context context) {
        return new FavoriteSongs(context);
    }

    public RealmResults<FavoriteSongRealm> getRealmResults() {
        return mRealmResults;
    }

    public List<FavoriteSongRealm> getList() {
        return Stream.of(mRealmResults).toList();
    }

    public List<Track> getSortedTrackList() {
        Map<String, Track> trackMap = toTrackMap(getTrackList());

        return Stream.of(getTrackIdList()).map(trackMap::get).collect(Collectors.toList());
    }

    public Map<String, Track> getTrackMap() {
        return toTrackMap(getTrackList());
    }

    private List<String> getTrackIdList() {
        return Stream.of(mRealmResults)
                .map(FavoriteSongRealm::getTrackId)
                .collect(Collectors.toList());
    }

    private List<Track> getTrackList() {
        TrackContentProvider provider = new TrackContentProvider(mContext);

        return provider.findById(getTrackIdList());
    }

    private Map<String, Track> toTrackMap(List<Track> trackList) {
        return Stream.of(trackList).collect(Collectors.toMap(Track::getId, track -> track));
    }

    private RealmResults<FavoriteSongRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteSongRealm.class).findAll().sort(FavoriteSongRealm.ID);
    }
}
