package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.ArtistDetailController;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.provider.ArtistAlbumsContentProvider;
import tokyo.tkw.thinmp.provider.ArtistContentProvider;
import tokyo.tkw.thinmp.provider.ArtistTracksContentProvider;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class ArtistDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        String artistId = getIntent().getStringExtra(Artist.ARTIST_ID);

        // artist取得
        Artist artist = Artist.createInstance(this, artistId);

        // 背景画像
        ImageView backgroundView = findViewById(R.id.background);
        GlideUtil.bitmap(artist.getThumbnailIdList(), backgroundView, GlideUtil.ARTIST_RESOURCE_ID);

        // サムネイル
        ImageView thumbnailView = findViewById(R.id.thumbnail);
        GlideUtil.bitmap(artist.getThumbnailIdList(), thumbnailView, GlideUtil.ARTIST_RESOURCE_ID);

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
        data.albumList = artist.getAlbumList();
        data.songs = getResources().getString(R.string.songs);
        data.trackList = artist.getTrackList();
        data.titleSpanSize = 2;
        data.albumListSpanSize = 1;
        data.trackListSpanSize = 2;

        controller.setData(data);
    }
}
