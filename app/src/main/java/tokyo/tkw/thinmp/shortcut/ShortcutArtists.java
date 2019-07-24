package tokyo.tkw.thinmp.shortcut;

import android.content.Context;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmResults;
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;
import tokyo.tkw.thinmp.realm.ShortcutRealm;

class ShortcutArtists {
    private ArtistContentProvider provider;
    private RealmResults<ShortcutRealm> realmResults;
    private List<ShortcutRealm> shortcutRealmList;
    private List<String> artistIdList;
    private List<Artist> artistList;
    private Map<String, Artist> artistMap;
    private Map<Integer, Shortcut> shortcutMap;

    private ShortcutArtists(Context context) {
        this.provider = new ArtistContentProvider(context);
    }

    static ShortcutArtists createInstance(Context context) {
        return new ShortcutArtists(context);
    }

    Map<Integer, Shortcut> getArtistShortcutMap() {
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
        return provider.findById(artistIdList);
    }

    private Map<String, Artist> getArtistMap() {
        return Stream.of(artistList).collect(Collectors.toMap(Artist::getId, artist -> artist));
    }

    private Map<Integer, Shortcut> getShortcutMap() {
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

    /**
     * アーティストが削除されていたらショートカットから削除する
     */
    private void validation() {
        if (exists()) return;

        remove();
    }

    private boolean exists() {
        return artistIdList.size() == artistList.size();
    }

    private void remove() {
        List<String> removeList = Stream.of(artistIdList)
                .filter(id -> !artistList.contains(id))
                .map(id -> id)
                .collect(Collectors.toList());

        ShortcutRegister register = ShortcutRegister.createInstance();
        register.delete(removeList, ShortcutRealm.TYPE_ARTIST);
    }
}
