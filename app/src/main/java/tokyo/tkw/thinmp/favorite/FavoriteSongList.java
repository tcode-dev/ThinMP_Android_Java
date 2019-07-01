package tokyo.tkw.thinmp.favorite;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.TrackContentProvider;
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

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

    public ArrayList<Track> getSortedTrackList() {
        Map<String, Track> trackMap = toTrackMap(getTrackList());

        return (ArrayList<Track>) Stream.of(getTrackIdList())
                .map(trackMap::get)
                .collect(Collectors.toList());
    }

    public Map<String, Track> getTrackMap() {
        return toTrackMap(getTrackList());
    }

    private ArrayList<String> getTrackIdList() {
        return (ArrayList<String>) Stream.of(mRealmResults)
                .map(FavoriteSongRealm::getTrackId)
                .collect(Collectors.toList());
    }

    private ArrayList<Track> getTrackList() {
        TrackContentProvider provider = new TrackContentProvider(mContext);

        return provider.findById(getTrackIdList());
    }

    private Map<String, Track> toTrackMap(ArrayList<Track> trackList) {
        return Stream.of(trackList).collect(Collectors.toMap(Track::getId, track -> track));
    }

    private RealmResults<FavoriteSongRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(FavoriteSongRealm.class).findAll().sort(FavoriteSongRealm.ID);
    }
}
