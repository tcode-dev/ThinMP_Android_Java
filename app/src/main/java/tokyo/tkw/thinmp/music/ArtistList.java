package tokyo.tkw.thinmp.music;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ArtistList
 */
public class ArtistList {
    private HashMap<String, Artist> mMap = new HashMap<>();
    private ArrayList<Artist> mList = new ArrayList<>();

    public void add(Track track) {
        String artistId = track.getArtistId();

        if (!mMap.containsKey(artistId)) {
            Artist artist = new Artist(
                    artistId,
                    track.getArtistName()
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
