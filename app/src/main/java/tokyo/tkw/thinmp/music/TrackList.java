package tokyo.tkw.thinmp.music;

import java.util.ArrayList;
import java.util.HashMap;

import tokyo.tkw.thinmp.model.Track;

/**
 * Created by tk on 2018/03/23.
 */

public class TrackList {
    private HashMap<String, Track> mMap = new HashMap<String, Track>();
    private ArrayList<Track> mList = new ArrayList<>();

    public void add(Track track) {
        mList.add(track);

        String id = track.getId();

        if (! mMap.containsKey(id)) {
            mMap.put(id, track);
        }
    }

    public ArrayList<Track> getList(){
        return mList;
    }

    public Track getTrack(String id) {
        return mMap.get(id);
    }
}