package tokyo.tkw.thinmp.shortcut;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.AlbumCollection;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.music.ArtistCollection;
import tokyo.tkw.thinmp.realm.ShortcutRealm;

public class ShortcutCollection {
    private Context context;
    private RealmResults<ShortcutRealm> realmResults;

    public ShortcutCollection(Context context) {
        this.context = context;
        this.realmResults = findAll();
    }

    public List<Shortcut> getList() {
        Map<Integer, Shortcut> artistMap = getArtistShortcutMap();
        Map<Integer, Shortcut> albumMap = getAlbumShortcutMap();

        return Stream.of(realmResults).map(shortcutRealm -> {
            switch (shortcutRealm.getType()) {
                case ShortcutRealm.TYPE_ARTIST:
                    return artistMap.get(shortcutRealm.getId());

                case ShortcutRealm.TYPE_ALBUM:
                    return albumMap.get(shortcutRealm.getId());

                default:
                    return null;
            }
        }).toList();
    }

    private Map<Integer, Shortcut> getArtistShortcutMap() {
        List<ShortcutRealm> shortcutRealmList = chunkByArtist();
        List<String> artistIdList = getItemIdList(shortcutRealmList);
        List<Artist> artistList = getArtistList(artistIdList);
        Map<String, Artist> artistMap = getArtistMap(artistList);

        return Stream.of(shortcutRealmList)
                .collect(Collectors.toMap(
                        ShortcutRealm::getId,
                        shortcutRealm -> {
                            Artist artist = artistMap.get(shortcutRealm.getItemId());
                            return new Shortcut(
                                    shortcutRealm.getId(),
                                    artist.getName(),
                                    ShortcutRealm.ARTIST,
                                    artist.getAlbumArtId()
                            );
                        }));
    }

    private Map<Integer, Shortcut> getAlbumShortcutMap() {
        List<ShortcutRealm> shortcutRealmList = chunkByAlbum();
        List<String> albumIdList = getItemIdList(shortcutRealmList);
        List<Album> albumList = getAlbumList(albumIdList);
        Map<String, Album> albumMap = getAlbumMap(albumList);

        return Stream.of(shortcutRealmList)
                .collect(Collectors.toMap(
                        ShortcutRealm::getId,
                        shortcutRealm -> {
                            Album album = albumMap.get(shortcutRealm.getItemId());
                            return new Shortcut(
                                    shortcutRealm.getId(),
                                    album.getName(),
                                    ShortcutRealm.ALBUM,
                                    album.getAlbumArtId()
                            );
                        }));
    }

    private Map<String, Artist> getArtistMap(List<Artist> artistList) {
        return Stream.of(artistList).collect(Collectors.toMap(Artist::getId, artist -> artist));
    }

    private Map<String, Album> getAlbumMap(List<Album> albumList) {
        return Stream.of(albumList).collect(Collectors.toMap(Album::getId, album -> album));
    }

    private List<Artist> getArtistList(List<String> artistIdList) {
        ArtistCollection artistCollection = ArtistCollection.createArtistCollectionInstance(context,
                artistIdList);

        return artistCollection.getList();
    }

    private List<Album> getAlbumList(List<String> albumIdList) {
        AlbumCollection albumCollection = AlbumCollection.createAlbumCollectionInstance(context,
                albumIdList);

        return albumCollection.getList();
    }

    private List<String> getItemIdList(List<ShortcutRealm> shortcutRealmList) {
        return Stream.of(shortcutRealmList).map(ShortcutRealm::getItemId).toList();
    }

    private List<ShortcutRealm> chunkByArtist() {
        return chunkBy(ShortcutRealm.TYPE_ARTIST);
    }

    private List<ShortcutRealm> chunkByAlbum() {
        return chunkBy(ShortcutRealm.TYPE_ALBUM);
    }

    private List<ShortcutRealm> chunkBy(int type) {
        return Stream.of(realmResults).filter(shortcutRealm -> shortcutRealm.getType() == type).toList();
    }

    private RealmResults<ShortcutRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(ShortcutRealm.class).findAll().sort(ShortcutRealm.ID, Sort.DESCENDING);
    }
}
