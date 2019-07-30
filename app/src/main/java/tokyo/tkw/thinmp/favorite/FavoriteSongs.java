package tokyo.tkw.thinmp.favorite;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.provider.TrackContentProvider;
import tokyo.tkw.thinmp.realm.FavoriteSongRealm;
import tokyo.tkw.thinmp.track.Track;

public class FavoriteSongs {
    private Realm realm;
    private TrackContentProvider provider;
    private RealmResults<FavoriteSongRealm> favoriteList;
    private List<String> trackIdList;
    private List<Track> trackList;
    private Map<String, Track> trackMap;
    private List<Track> sortedTrackList;

    private FavoriteSongs(Context context) {
        realm = Realm.getDefaultInstance();
        provider = new TrackContentProvider(context);
    }

    public static FavoriteSongs createInstance(Context context) {
        return new FavoriteSongs(context);
    }

    public List<Track> getSortedTrackList() {
        favoriteList = getFavoriteList();
        trackIdList = getTrackIdList();
        trackList = getTrackList();
        trackMap = getTrackMap();
        sortedTrackList = sortTrackList();

        validation();

        return sortedTrackList;
    }

    private RealmResults<FavoriteSongRealm> getFavoriteList() {
        return realm.where(FavoriteSongRealm.class).findAll().sort(FavoriteSongRealm.ORDER);
    }

    private List<String> getTrackIdList() {
        return Stream.of(favoriteList).map(FavoriteSongRealm::getTrackId).collect(Collectors.toList());
    }

    private List<Track> getTrackList() {
        return provider.findById(trackIdList);
    }

    private Map<String, Track> getTrackMap() {
        return Stream.of(trackList).collect(Collectors.toMap(Track::getId, track -> track));
    }

    private List<Track> sortTrackList() {
        return Stream.of(trackIdList).filter(trackMap::containsKey).map(trackMap::get).collect(Collectors.toList());
    }

    private void validation() {
        if (isDeleted()) {
            remove();
        }
    }

    private boolean isDeleted() {
        return trackIdList.size() != sortedTrackList.size();
    }

    private void remove() {
        List<String> removeList = Stream.of(trackIdList)
                .filter(id -> !trackMap.containsKey(id))
                .map(id -> id)
                .collect(Collectors.toList());

        FavoriteSongRegister register = FavoriteSongRegister.createInstance();
        register.delete(removeList);
    }
}
