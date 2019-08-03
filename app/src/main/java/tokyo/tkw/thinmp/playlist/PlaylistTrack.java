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
import tokyo.tkw.thinmp.register.delete.PlaylistTrackDeleter;
import tokyo.tkw.thinmp.track.Track;

class PlaylistTrack {
    private TrackContentProvider trackContentProvider;
    private List<String> trackIdList;
    private Map<String, Track> trackMap;
    private RealmList<PlaylistTrackRealm> trackRealmList;
    private List<Track> trackList;
    private List<Track> sortedTrackList;
    private String albumArtId;

    private PlaylistTrack(Context context, RealmList<PlaylistTrackRealm> trackRealmList) {
        this.trackContentProvider = new TrackContentProvider(context);
        this.trackRealmList = trackRealmList;
        this.trackIdList = getTrackIdList();
        this.trackList = getTrackList();
        this.trackMap = toTrackMap();
        this.sortedTrackList = sortTrackList();
        this.albumArtId = getFirstTrackAlbumArtId();

        validation();
    }

    static PlaylistTrack createInstance(Context context, RealmList<PlaylistTrackRealm> trackRealmList) {
        return new PlaylistTrack(context, trackRealmList);
    }

    String getAlbumArtId() {
        return albumArtId;
    }

    List<Track> getSortedTrackList() {
        return sortedTrackList;
    }

    List<String> getTrackIdList() {
        return Stream.of(trackRealmList)
                .map(PlaylistTrackRealm::getTrackId)
                .collect(Collectors.toList());
    }

    private List<Track> sortTrackList() {
        return Stream.of(trackIdList).filter(trackMap::containsKey).map(trackMap::get).collect(Collectors.toList());
    }

    private String getFirstTrackAlbumArtId() {
        Optional<Track> track = Stream.of(sortedTrackList).findFirst();

        if (track.isEmpty()) return null;

        return track.get().getAlbumArtId();
    }

    private List<String> getUniqueTrackIdList() {
        return Stream.of(trackIdList)
                .distinct()
                .collect(Collectors.toList());
    }

    private Map<String, Track> toTrackMap() {
        return Stream.of(trackList).collect(Collectors.toMap(Track::getId, track -> track));
    }

    private List<Track> getTrackList() {
        return trackContentProvider.findById(getUniqueTrackIdList());
    }

    private void validation() {
        if (isDeleted()) {
            delete();
        }
    }

    private boolean isDeleted() {
        return trackIdList.size() != sortedTrackList.size();
    }

    private void delete() {
        List<String> deleteList = Stream.of(trackIdList)
                .filter(id -> !trackMap.containsKey(id))
                .map(id -> id)
                .collect(Collectors.toList());

        PlaylistTrackDeleter playlistTrackDeleter = PlaylistTrackDeleter.createInstance();
        playlistTrackDeleter.delete(deleteList);
    }
}
