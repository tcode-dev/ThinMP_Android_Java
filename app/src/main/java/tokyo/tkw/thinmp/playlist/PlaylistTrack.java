package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.TrackContentProvider;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistTrack {
    private Context mContext;
    private int mPlaylistId;
    private RealmList<PlaylistTrackRealm> mTrackRealmList;

    public PlaylistTrack(Context context, int playlistId) {
        mContext = context;
        mPlaylistId = playlistId;
        mTrackRealmList = getTrackRealmList();
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
        return (ArrayList<String>) Stream.of(mTrackRealmList)
                .map(PlaylistTrackRealm::getTrackId)
                .collect(Collectors.toList());
    }

    private Map<String, Track> toTrackMap(ArrayList<Track> trackList) {
        return Stream.of(trackList).collect(Collectors.toMap(Track::getId, track -> track));
    }

    private RealmList<PlaylistTrackRealm> getTrackRealmList() {
        PlaylistRealm playlistRealm = PlaylistRealm.createInstance(mPlaylistId);

        return playlistRealm.getTracks();
    }

    private ArrayList<Track> getTrackList() {
        TrackContentProvider provider = new TrackContentProvider(mContext);

        return provider.findById(getTrackIdList());
    }
}
