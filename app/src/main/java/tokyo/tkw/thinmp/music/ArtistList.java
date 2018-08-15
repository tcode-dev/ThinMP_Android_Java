package tokyo.tkw.thinmp.music;

import java.util.ArrayList;
import java.util.HashMap;

import tokyo.tkw.thinmp.model.Artist;
import tokyo.tkw.thinmp.model.Track;

/**
 * Created by tk on 2018/03/23.
 */

public class ArtistList {
    private HashMap<String, Artist> mMap = new HashMap<String, Artist>();
    private ArrayList<Artist> mList = new ArrayList<Artist>();

    public void add(Track track) {
        String artistId = track.getArtistId();

        if (! mMap.containsKey(artistId)) {
            Artist artist = new Artist(
                    artistId,
                    track.getArtistName(),
                    track.getThumbnailId()
            );
            mMap.put(artistId, artist);
            mList.add(artist);
        }

        Artist artist = mMap.get(artistId);
        artist.addTrackId(track.getId());
        artist.addAlbumId(track.getAlbumId());

        mMap.put(artistId, artist);
    }

    public ArrayList getList() {
        return mList;
    }

    /**
     * アーティストを取得
     *
     * @return
     */
    public Artist getArtist(String id) {
        return mMap.get(id);
    }
}