package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Optional;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.AlbumArtContentProvider;
import tokyo.tkw.thinmp.provider.TrackContentProvider;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

class PlaylistTrack {
    private TrackContentProvider trackContentProvider;
    private AlbumArtContentProvider albumArtContentProvider;
    private List<String> trackIdList;
    private List<String> uniqueTrackIdList;
    private Map<String, Track> trackMap;
    private List<String> albumIdList;
    private Map<String, String> albumArtMap;
    private RealmList<PlaylistTrackRealm> trackRealmList;
    private List<Track> trackList;
    private String albumArtId;

    private PlaylistTrack(Context context, RealmList<PlaylistTrackRealm> trackRealmList) {
        this.trackContentProvider = new TrackContentProvider(context);
        this.albumArtContentProvider = new AlbumArtContentProvider(context);
        this.trackRealmList = trackRealmList;
        this.trackIdList = getTrackIdList();
        this.uniqueTrackIdList = getUniqueTrackIdList();
        this.albumIdList = getAlbumIdList();
        List<Track> trackList = getTrackList();
        this.albumArtMap = getAlbumArtMap(trackList);
        this.trackList = updateTrackList(trackList);
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
        return Optional.ofNullable(albumArtId);
    }

    private String getFirstTrackAlbumArtId() {
        Optional<String> TrackId = Stream.of(uniqueTrackIdList)
                .filter(albumArtMap::containsKey)
                .findFirst();

        return albumArtMap.get(TrackId.get());
    }

    private List<Album> getAlbumList() {
        return albumArtContentProvider.findByTrack(uniqueTrackIdList);
    }

    private List<String> getAlbumIdList() {
        return Stream.of(getAlbumList())
                .map(Album::getId)
                .collect(Collectors.toList());
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

    private List<Track> updateTrackList(List<Track> trackList) {
        return Stream.of(trackList).map(track -> {
            track.setAlbumArtId(albumArtMap.get(track.getId()));
            return track;
        }).collect(Collectors.toList());
    }

    private Map<String, String> getAlbumArtMap(List<Track> trackList) {
        System.out.println(trackList);
        return Stream.of(trackList)
                .filter(track -> albumIdList.contains(track.getAlbumId()))
                .distinctBy(Track::getId)
                .collect(Collectors.toMap(Track::getId, Track::getAlbumArtId));
    }
}
