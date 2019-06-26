package tokyo.tkw.thinmp.playlist;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.TracksContentProvider;
import tokyo.tkw.thinmp.realm.PlaylistTrackRealm;

public class PlaylistTrack {
    private Context mContext;
    private int mPlaylistId;
    private RealmResults<PlaylistTrackRealm> mRealmResults;

    public PlaylistTrack(Context context, int playlistId) {
        mContext = context;
        mPlaylistId = playlistId;
        mRealmResults = findAll();
    }

    public RealmResults<PlaylistTrackRealm> getRealmResults() {
        return mRealmResults;
    }

    public ArrayList<PlaylistTrackRealm> getList() {
        return (ArrayList<PlaylistTrackRealm>) Stream.of(mRealmResults).toList();
    }

    public ArrayList<Track> getTrackList() {
        ArrayList<String> trackIdList =
                (ArrayList<String>) Stream.of(mRealmResults).map(PlaylistTrackRealm::getTrackId).collect(Collectors.toList());

        TracksContentProvider tracksContentProvider = new TracksContentProvider(mContext,
                trackIdList);

        return tracksContentProvider.getList();
    }

    public Map<String, Track> getTrackMap() {
        return getTrackMap(getTrackList());
    }

    public Map<String, Track> getTrackMap(ArrayList<Track> trackList) {
        return Stream.of(trackList).collect(Collectors.toMap(track -> track.getId(),
                track -> track));
    }

    private RealmResults<PlaylistTrackRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(PlaylistTrackRealm.class).equalTo(PlaylistTrackRealm.PLAYLIST_ID,
                mPlaylistId).findAll();
    }
}
