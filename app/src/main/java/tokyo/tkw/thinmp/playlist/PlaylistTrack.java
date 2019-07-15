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
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistTrack {
    private Context mContext;
    private int mPlaylistId;
    private RealmList<PlaylistTrackRealm> mTrackRealmList;
    private List<Track> mTrackList;

    public PlaylistTrack(Context context, int playlistId) {
        mContext = context;
        mPlaylistId = playlistId;
        mTrackRealmList = getTrackRealmList();
        mTrackList = getTrackList();
    }

    public List<Track> getSortedTrackList() {
        Map<String, Track> trackMap = toTrackMap(mTrackList);

        return Stream.of(toTrackIdList()).map(trackMap::get).collect(Collectors.toList());
    }

    public Map<String, Track> getTrackMap() {
        return toTrackMap(mTrackList);
    }

    public String getFirstTrackAlbumArtId() {
        Map<String, String> albumArtMap = toAlbumArtMap();

        Optional<String> TrackIdOpt = Stream.of(toTrackIdList()).filter(albumArtMap::containsKey).findFirst();

        return albumArtMap.get(TrackIdOpt.get());
    }

    private List<Album> getAlbumList() {
        AlbumArtContentProvider provider = new AlbumArtContentProvider(mContext);

        return provider.findById(toAlbumIdList());
    }

    private List<String> getAlbumIdList() {
        return Stream.of(getAlbumList())
                .map(Album::getId)
                .collect(Collectors.toList());
    }

    private List<String> toTrackIdList() {
        return Stream.of(mTrackRealmList)
                .map(PlaylistTrackRealm::getTrackId)
                .collect(Collectors.toList());
    }

    private List<String> toAlbumIdList() {
        return Stream.of(mTrackRealmList)
                .map(PlaylistTrackRealm::getAlbumArtId)
                .collect(Collectors.toList());
    }

    private Map<String, Track> toTrackMap(List<Track> trackList) {
        return Stream.of(trackList).collect(Collectors.toMap(Track::getId, track -> track));
    }

    private RealmList<PlaylistTrackRealm> getTrackRealmList() {
        Playlists playlists = Playlists.createInstance(mContext);
        PlaylistRealm playlistRealm = playlists.findById(mPlaylistId);

        return playlistRealm.getTrackRealmList();
    }

    private List<Track> getTrackList() {
        TrackContentProvider provider = new TrackContentProvider(mContext);
        List<Track> trackList = provider.findById(toTrackIdList());
        Map<String, String> albumArtMap = toAlbumArtMap();

        return Stream.of(trackList).map(track -> {
            track.setAlbumArtId(albumArtMap.get(track.getId()));
            return track;
        }).collect(Collectors.toList());
    }

    private Map<String, String> toAlbumArtMap() {
        List<String> albumList = getAlbumIdList();

        return Stream.of(mTrackRealmList)
                .filter(realm -> albumList.contains(realm.getAlbumArtId()))
                .distinctBy(PlaylistTrackRealm::getTrackId)
                .collect(Collectors.toMap(PlaylistTrackRealm::getTrackId,
                        PlaylistTrackRealm::getAlbumArtId));
    }
}
