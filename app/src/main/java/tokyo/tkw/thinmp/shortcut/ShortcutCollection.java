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
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.playlist.Playlists;
import tokyo.tkw.thinmp.realm.PlaylistRealm;
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
        Map<Integer, Shortcut> playlistMap = getPlaylistShortcutMap();

        return toShortcutList(artistMap, albumMap, playlistMap);
    }

    private List<Shortcut> toShortcutList(Map<Integer, Shortcut> artistMap, Map<Integer,
            Shortcut> albumMap, Map<Integer, Shortcut> playlistMap) {
        return Stream.of(realmResults).map(shortcutRealm -> {
            switch (shortcutRealm.getType()) {
                case ShortcutRealm.TYPE_ARTIST:
                    return artistMap.get(shortcutRealm.getId());

                case ShortcutRealm.TYPE_ALBUM:
                    return albumMap.get(shortcutRealm.getId());

                case ShortcutRealm.TYPE_PLAYLIST:
                    return playlistMap.get(shortcutRealm.getId());

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

        return toArtistShortcutMap(shortcutRealmList, artistMap);
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

    private Map<Integer, Shortcut> getAlbumShortcutMap() {
        List<ShortcutRealm> shortcutRealmList = chunkByAlbum();
        List<String> albumIdList = getItemIdList(shortcutRealmList);
        List<Album> albumList = getAlbumList(albumIdList);
        Map<String, Album> albumMap = getAlbumMap(albumList);

        return toAlbumShortcutMap(shortcutRealmList, albumMap);
    }

    private Map<Integer, Shortcut> toAlbumShortcutMap(List<ShortcutRealm> shortcutRealmList,
                                                      Map<String, Album> albumMap) {
        return Stream.of(shortcutRealmList)
                .collect(Collectors.toMap(
                        ShortcutRealm::getId,
                        shortcutRealm -> {
                            Album album = albumMap.get(shortcutRealm.getItemId());
                            return new Shortcut(
                                    shortcutRealm.getItemId(),
                                    album.getName(),
                                    ShortcutRealm.ALBUM,
                                    album.getAlbumArtId()
                            );
                        }));
    }

    private Map<Integer, Shortcut> getPlaylistShortcutMap() {
        List<ShortcutRealm> shortcutRealmList = chunkByPlaylist();
        List<String> playlistIdList = getItemIdList(shortcutRealmList);
        List<Integer> playlistIds = Stream.of(playlistIdList).map(Integer::parseInt).toList();
        List<PlaylistRealm> playLists = getPlayList(playlistIds);
        Map<String, PlaylistRealm> playlistMap = getPlaylistMap(playLists);

        return toPlaylistShortcutMap(shortcutRealmList, playlistMap);
    }

    private Map<Integer, Shortcut> toPlaylistShortcutMap(List<ShortcutRealm> shortcutRealmList,
                                                         Map<String, PlaylistRealm> playlistMap) {
        return Stream.of(shortcutRealmList)
                .collect(Collectors.toMap(
                        ShortcutRealm::getId,
                        shortcutRealm -> {
                            PlaylistRealm playlistRealm =
                                    playlistMap.get(shortcutRealm.getItemId());
                            Playlist playlist = Playlist.createInstance(context,
                                    playlistRealm.getId());
                            return new Shortcut(
                                    shortcutRealm.getItemId(),
                                    playlistRealm.getName(),
                                    ShortcutRealm.PLAYLIST,
                                    playlist.getAlbumArtId()
                            );
                        }));
    }

    private Map<String, Artist> getArtistMap(List<Artist> artistList) {
        return Stream.of(artistList).collect(Collectors.toMap(Artist::getId, artist -> artist));
    }

    private Map<String, Album> getAlbumMap(List<Album> albumList) {
        return Stream.of(albumList).collect(Collectors.toMap(Album::getId, album -> album));
    }

    private Map<String, PlaylistRealm> getPlaylistMap(List<PlaylistRealm> playlists) {
        return Stream.of(playlists)
                .collect(Collectors.toMap(
                        playlistRealm -> Integer.toString(playlistRealm.getId()),
                        playlistRealm -> playlistRealm));
    }

    private List<Artist> getArtistList(List<String> artistIdList) {
        ArtistCollection artistCollection = ArtistCollection.createArtistCollectionInstance(context,
                artistIdList);

        return artistCollection.getList();
    }

    private List<Album> getAlbumList(List<String> albumIdList) {
        AlbumCollection albumCollection = AlbumCollection.createInstance(context);

        return albumCollection.findById(albumIdList);
    }

    private List<PlaylistRealm> getPlayList(List<Integer> playlistIdList) {
        Playlists playlists = Playlists.createInstance(context, playlistIdList);

        return playlists.getPlaylistRealmList();
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

    private List<ShortcutRealm> chunkByPlaylist() {
        return chunkBy(ShortcutRealm.TYPE_PLAYLIST);
    }

    private List<ShortcutRealm> chunkBy(int type) {
        return Stream.of(realmResults).filter(shortcutRealm -> shortcutRealm.getType() == type).toList();
    }

    private RealmResults<ShortcutRealm> findAll() {
        Realm realm = Realm.getDefaultInstance();

        return realm.where(ShortcutRealm.class).findAll().sort(ShortcutRealm.ID, Sort.DESCENDING);
    }
}
