package tokyo.tkw.thinmp.shortcut;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.artist.ArtistAlbumArt;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;
import tokyo.tkw.thinmp.realm.ShortcutRealm;
import tokyo.tkw.thinmp.register.delete.ShortcutDeleter;

class ShortcutArtists {
    private ArtistContentProvider artistContentProvider;
    private ArtistAlbumArt artistAlbumArt;
    private RealmResults<ShortcutRealm> realmResults;
    private List<ShortcutRealm> shortcutRealmList;
    private List<String> artistIdList;
    private List<Artist> artistList;
    private Map<String, Artist> artistMap;
    private Map<String, Shortcut> shortcutMap;

    private ShortcutArtists(Context context) {
        artistContentProvider = new ArtistContentProvider(context);
        artistAlbumArt = ArtistAlbumArt.createInstance(context);
    }

    static ShortcutArtists createInstance(Context context) {
        return new ShortcutArtists(context);
    }

    Map<String, Shortcut> getArtistShortcutMap() {
        realmResults = findAll();
        shortcutRealmList = getShortcutRealmList();
        artistIdList = getItemIdList();
        artistList = getArtistList();
        artistMap = getArtistMap();
        shortcutMap = getShortcutMap();

        validation();

        return shortcutMap;
    }

    private RealmResults<ShortcutRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(ShortcutRealm.class).equalTo(ShortcutRealm.TYPE, ShortcutRealm.TYPE_ARTIST).findAll();
    }

    private List<ShortcutRealm> getShortcutRealmList() {
        return Stream.of(realmResults).toList();
    }

    private List<String> getItemIdList() {
        return Stream.of(shortcutRealmList).map(ShortcutRealm::getItemId).toList();
    }

    private List<Artist> getArtistList() {
        List<Artist> artistList = artistContentProvider.findById(artistIdList);

        return artistAlbumArt.mapAlbumArt(artistList);
    }

    private Map<String, Artist> getArtistMap() {
        return Stream.of(artistList).collect(Collectors.toMap(Artist::getId, artist -> artist));
    }

    private Map<String, Shortcut> getShortcutMap() {
        return Stream.of(shortcutRealmList)
                .filter(shortcutRealm -> artistMap.containsKey(shortcutRealm.getItemId()))
                .collect(Collectors.toMap(
                        ShortcutRealm::getId,
                        shortcutRealm -> {
                            Artist artist = artistMap.get(shortcutRealm.getItemId());
                            return new Shortcut(
                                    shortcutRealm.getItemId(),
                                    artist.getName(),
                                    ShortcutRealm.ARTIST,
                                    artist.getAlbumArtId()
                            );
                        }));
    }

    private void validation() {
        if (isDeleted()) {
            delete();
        }
    }

    private boolean isDeleted() {
        return artistIdList.size() != artistList.size();
    }

    private void delete() {
        List<String> removeList = Stream.of(artistIdList)
                .filter(id -> !artistList.contains(id))
                .map(id -> id)
                .collect(Collectors.toList());

        ShortcutDeleter shortcutDeleter = ShortcutDeleter.createInstance();
        shortcutDeleter.delete(removeList, ShortcutRealm.TYPE_ARTIST);
    }
}
