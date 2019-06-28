package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

import io.realm.RealmList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.TracksContentProvider;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistTrack {
    private Context mContext;
    private int mPlaylistId;
    private RealmList<PlaylistTrackRealm> mRealmList;

    public PlaylistTrack(Context context, int playlistId) {
        mContext = context;
        mPlaylistId = playlistId;
        mRealmList = getRealmList();
    }

    public ArrayList<Track> getTrackList() {
        Map<String, Track> trackMap = toTrackMap(getList());

        return (ArrayList<Track>) Stream.of(getTrackIdList())
                .map(trackMap::get)
                .collect(Collectors.toList());
    }

    public Map<String, Track> getTrackMap() {
        return toTrackMap(getList());
    }

    private ArrayList<String> getTrackIdList() {
        return (ArrayList<String>) Stream.of(mRealmList)
                .map(PlaylistTrackRealm::getTrackId)
                .collect(Collectors.toList());
    }

    private Map<String, Track> toTrackMap(ArrayList<Track> trackList) {
        return Stream.of(trackList).collect(Collectors.toMap(Track::getId, track -> track));
    }

    private RealmList<PlaylistTrackRealm> getRealmList() {
        PlaylistRealm playlistRealm = PlaylistRealm.createInstance(mPlaylistId);

        return playlistRealm.getTracks();
    }

    private ArrayList<Track> getList() {
        TracksContentProvider provider = new TracksContentProvider(mContext, getTrackIdList());

        return provider.getList();
    }
}
