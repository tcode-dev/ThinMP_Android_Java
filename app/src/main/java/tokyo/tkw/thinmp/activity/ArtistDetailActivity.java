package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.ArtistDetailController;
import tokyo.tkw.thinmp.listener.ArtistMenuClickListener;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.AlbumCollection;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.music.TrackCollection;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class ArtistDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artist_detail);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        String artistId = getIntent().getStringExtra(Artist.ARTIST_ID);

        // artist取得
        Artist artist = Artist.createInstance(this, artistId);

        AlbumCollection albumCollection = AlbumCollection.createInstance(this);
        List<Album> artistAlbumList = albumCollection.findByArtist(artistId);
        String albumArtId = albumCollection.findFirstAlbumArtId(artistAlbumList);

        TrackCollection trackCollection =
                TrackCollection.createArtistTrackCollectionInstance(this, artistId);

        // 背景画像
        ImageView backgroundView = findViewById(R.id.background);
        GlideUtil.bitmap(albumArtId, backgroundView, GlideUtil.ARTIST_RESOURCE_ID);

        // アルバムアート
        ImageView albumArtView = findViewById(R.id.albumArt);
        GlideUtil.bitmap(albumArtId, albumArtView, GlideUtil.ARTIST_RESOURCE_ID);

        // タイトル
        ResponsiveTextView titleView = findViewById(R.id.title);
        titleView.setText(artist.getName());

        // recyclerView
        RecyclerView recyclerView = findViewById(R.id.list);
        GridLayoutManager layout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layout);

        // epoxy
        ArtistDetailController controller = new ArtistDetailController();
        recyclerView.setAdapter(controller.getAdapter());
        controller.setSpanCount(2);
        layout.setSpanSizeLookup(controller.getSpanSizeLookup());

        ArtistDetailController.Data data = new ArtistDetailController.Data();
        data.albums = getResources().getString(R.string.albums);
        data.albumList = artistAlbumList;
        data.songs = getResources().getString(R.string.songs);
        data.trackList = trackCollection.getList();
        data.titleSpanSize = 2;
        data.albumListSpanSize = 1;
        data.trackListSpanSize = 2;

        controller.setData(data);

        ImageView menuView = findViewById(R.id.menu);
        menuView.setOnClickListener(new ArtistMenuClickListener(artistId));
    }
}
