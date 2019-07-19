package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.provider.TrackContentProvider;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;
import tokyo.tkw.thinmp.track.Track;

class PlaylistTrack {
    private TrackContentProvider trackContentProvider;
    private List<String> trackIdList;
    private List<String> uniqueTrackIdList;
    private Map<String, Track> trackMap;
    private RealmList<PlaylistTrackRealm> trackRealmList;
    private List<Track> trackList;
    private Optional<String> albumArtId;

    private PlaylistTrack(Context context, RealmList<PlaylistTrackRealm> trackRealmList) {
        this.trackContentProvider = new TrackContentProvider(context);
        this.trackRealmList = trackRealmList;
        this.trackIdList = getTrackIdList();
        this.uniqueTrackIdList = getUniqueTrackIdList();
        this.trackList = getTrackList();
        this.albumArtId = getFirstTrackAlbumArtId();
        this.trackMap = toTrackMap();
    }

    static PlaylistTrack createInstance(Context context,
                                        RealmList<PlaylistTrackRealm> trackRealmList) {
        return new PlaylistTrack(context, trackRealmList);
    }

    List<Track> getSortedTrackList() {
        return Stream.of(trackIdList).map(trackMap::get).collect(Collectors.toList());
    }

    Map<String, Track> getTrackMap() {
        return trackMap;
    }

    Optional<String> getAlbumArtId() {
        return albumArtId;
    }

    private Optional<String> getFirstTrackAlbumArtId() {
        return Stream.of(trackList).findFirst().get().getAlbumArtId();
    }

    private List<String> getTrackIdList() {
        return Stream.of(trackRealmList)
                .map(PlaylistTrackRealm::getTrackId)
                .collect(Collectors.toList());
    }

    private List<String> getUniqueTrackIdList() {
        return Stream.of(trackRealmList)
                .map(PlaylistTrackRealm::getTrackId)
                .distinct()
                .collect(Collectors.toList());
    }

    private Map<String, Track> toTrackMap() {
        return Stream.of(trackList).collect(Collectors.toMap(Track::getId, track -> track));
    }

    private List<Track> getTrackList() {
        return trackContentProvider.findById(uniqueTrackIdList);
    }
}
