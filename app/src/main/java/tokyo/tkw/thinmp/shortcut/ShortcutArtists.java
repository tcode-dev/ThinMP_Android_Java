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

    private ShortcutArtists(Context context) {
        this.provider = new ArtistContentProvider(context);
    }

    static ShortcutArtists createInstance(Context context) {
        return new ShortcutArtists(context);
    }

    Map<Integer, Shortcut> getArtistShortcutMap() {
        RealmResults<ShortcutRealm> realmResults = findAll();
        List<ShortcutRealm> shortcutRealmList = Stream.of(realmResults).toList();
        List<String> artistIdList = getItemIdList(shortcutRealmList);
        List<Artist> artistList = getArtistList(artistIdList);
        Map<String, Artist> artistMap = getArtistMap(artistList);

        return toArtistShortcutMap(shortcutRealmList, artistMap);
    }

    private RealmResults<ShortcutRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(ShortcutRealm.class).equalTo(ShortcutRealm.TYPE, ShortcutRealm.TYPE_ARTIST).findAll();
    }

    private List<String> getItemIdList(List<ShortcutRealm> shortcutRealmList) {
        return Stream.of(shortcutRealmList).map(ShortcutRealm::getItemId).toList();
    }

    private List<Artist> getArtistList(List<String> artistIdList) {
        return provider.findById(artistIdList);
    }

    private Map<String, Artist> getArtistMap(List<Artist> artistList) {
        return Stream.of(artistList).collect(Collectors.toMap(Artist::getId, artist -> artist));
    }

    private Map<Integer, Shortcut> toArtistShortcutMap(List<ShortcutRealm> shortcutRealmList,
                                                       Map<String, Artist> artistMap) {
        return Stream.of(shortcutRealmList)
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
}
