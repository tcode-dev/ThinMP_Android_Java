package tokyo.tkw.thinmp.music;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * AlbumList
 */
public class AlbumList {
    private HashMap<String, Album> mMap = new HashMap<>();
    private ArrayList<Album> mAlbumList = new ArrayList<>();

    public void add(Track track) {
        String albumId = track.getAlbumId();

        if (! mMap.containsKey(albumId)) {
            Album album = new Album(
                    albumId,
                    track.getAlbumName(),
                    track.getArtistId(),
                    track.getArtistName(),
                    track.getThumbnailId()
            );
            mMap.put(albumId, album);
            mAlbumList.add(album);
        }

        Album album = mMap.get(albumId);
        album.addTrackId(track.getId());

        mMap.put(albumId, album);
    }

    public ArrayList getList() {
        return mAlbumList;
    }

    public Album getAlbum(String id) {
        return mMap.get(id);
    }
}
