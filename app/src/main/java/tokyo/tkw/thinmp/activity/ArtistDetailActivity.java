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

        String artistId = getIntent().getStringExtra("artistId");

        // artist取得
        ArtistContentProvider artistContentProvider = new ArtistContentProvider(this, artistId);
        Artist artist = artistContentProvider.get();

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

        ArtistAlbumsContentProvider artistAlbumsContentProvider =
                new ArtistAlbumsContentProvider(this, artistId);
        ArtistTracksContentProvider artistTracksContentProvider =
                new ArtistTracksContentProvider(this, artistId);
        ArtistDetailController.Data data = new ArtistDetailController.Data();
        data.albums = getResources().getString(R.string.albums);
        data.albumList = artistAlbumsContentProvider.getList();
        data.songs = getResources().getString(R.string.songs);
        data.trackList = artistTracksContentProvider.getList();
        data.titleSpanSize = 2;
        data.albumListSpanSize = 1;
        data.trackListSpanSize = 2;

        controller.setData(data, this);
    }
}
