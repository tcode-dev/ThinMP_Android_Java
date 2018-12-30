package tokyo.tkw.thinmp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.ArtistAlbumListAdapter;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.plugin.RSBlurProcessor;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;

public class ArtistActivity extends AppCompatActivity {
    private ImageView mBackgroundView;
    private ImageView mThumbnailView;
    private RecyclerView mListView;

    private ArrayList<Album> mAlbumIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        String artistId = getIntent().getStringExtra("artistId");
        Artist artist = MusicList.getArtist(artistId);

        setView();

        //サムネイル
        Bitmap thumbnail = new ThumbnailProvider().getThumbnail(artist.getThumbnailId());
        mThumbnailView.setImageBitmap(thumbnail);

        //背景画像(同じ画像はキャッシュしているので、サムネイルもぼかしが掛からないようにnewして取得)
        Bitmap backgroundBitmap = new ThumbnailProvider().getThumbnail(artist.getThumbnailId());
        RenderScript rs = RenderScript.create(this);
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs);
        Bitmap blurBitMap = rsBlurProcessor.blur(backgroundBitmap, 20f, 3);
        mBackgroundView.setImageBitmap(blurBitMap);

        //アルバム一覧
        mAlbumIdList = MusicList.getArtistAlbumList(artistId);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(artist.getName());
        setSupportActionBar(toolbar);

        setAdapter();
    }

    private void setView() {
        mBackgroundView = findViewById(R.id.background);
        mThumbnailView = findViewById(R.id.thumbnail);
        mListView = findViewById(R.id.list);
    }

    private void setAdapter() {
        ArtistAlbumListAdapter adapter = new ArtistAlbumListAdapter(this, mAlbumIdList);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mListView.setLayoutManager(layout);
        mListView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        mListView.addItemDecoration(dividerItemDecoration);
    }
}
