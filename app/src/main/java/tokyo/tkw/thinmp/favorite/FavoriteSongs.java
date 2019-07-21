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

    public FavoriteSongs(Context context) {
        realm = Realm.getDefaultInstance();
        provider = new TrackContentProvider(context);
    }

    public static FavoriteSongs createInstance(Context context) {
        return new FavoriteSongs(context);
    }

    public List<Track> getSortedTrackList() {
        RealmResults<FavoriteSongRealm> realmResults =
                realm.where(FavoriteSongRealm.class).findAll().sort(FavoriteSongRealm.ID);
        List<String> trackIdList = Stream.of(realmResults)
                .map(FavoriteSongRealm::getTrackId).collect(Collectors.toList());
        List<Track> trackList = provider.findById(trackIdList);
        Map<String, Track> trackMap = Stream.of(trackList).collect(Collectors.toMap(Track::getId, track -> track));

        return Stream.of(trackIdList).map(trackMap::get).collect(Collectors.toList());
    }
}
