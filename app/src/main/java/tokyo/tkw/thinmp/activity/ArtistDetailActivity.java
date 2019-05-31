package tokyo.tkw.thinmp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.ArtistDetailController;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.plugin.RSBlurProcessor;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class ArtistDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        String artistId = getIntent().getStringExtra("artistId");

        // artist取得
        Artist artist = MusicList.getArtist(artistId);

        // サムネイル
        ImageView thumbnailView = findViewById(R.id.thumbnail);
        Bitmap thumbnailBitmap = artist.getThumbnail();
        thumbnailView.setImageBitmap(thumbnailBitmap);

        // 背景画像
        ImageView backgroundView = findViewById(R.id.background);
        Bitmap backgroundBitmap = thumbnailBitmap.copy(Bitmap.Config.ARGB_8888, true);
        RenderScript rs = RenderScript.create(this);
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs);
        Bitmap blurBitMap = rsBlurProcessor.blur(backgroundBitmap, 20f, 3);
        backgroundView.setImageBitmap(blurBitMap);

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
        data.albumList = MusicList.getArtistAlbumList(artistId);
        data.songs = getResources().getString(R.string.songs);
        data.trackList = MusicList.getArtistTrackList(artistId);
        data.titleSpanSize = 2;
        data.albumListSpanSize = 1;
        data.trackListSpanSize = 2;

        controller.setData(data, this);
    }
}
