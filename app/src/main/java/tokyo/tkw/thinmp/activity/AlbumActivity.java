package tokyo.tkw.thinmp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumTrackListAdapter;
import tokyo.tkw.thinmp.fragment.MiniPlayerFragment;
import tokyo.tkw.thinmp.menu.TrackMenu;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.plugin.RSBlurProcessor;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;

public class AlbumActivity extends AppCompatActivity implements AlbumTrackListAdapter.OnTrackListItemClickListener {
    private ImageView mBackgroundView;
    private ImageView mThumbnailView;
    private RecyclerView mListView;

    private ArrayList<Track> mTrackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        setView();

        String albumId = getIntent().getStringExtra("albumId");
        Album album = MusicList.getAlbum(albumId);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(album.getArtistName());
        toolbar.setSubtitle(album.getName());
        setSupportActionBar(toolbar);

        //サムネイル
        Bitmap thumbnailBitmap = new ThumbnailProvider().getThumbnail(album.getThumbnailId());
        mThumbnailView.setImageBitmap(thumbnailBitmap);

        //背景画像(同じ画像はキャッシュしているので、サムネイルもぼかしが掛からないように別インスタンスで取得)
        Bitmap backgroundBitmap = new ThumbnailProvider().getThumbnail(album.getThumbnailId());
        RenderScript rs = RenderScript.create(this);
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs);
        Bitmap blurBitMap = rsBlurProcessor.blur(backgroundBitmap, 20f, 3);
        mBackgroundView.setImageBitmap(blurBitMap);

        //曲一覧
        mTrackList = MusicList.getAlbumTrackList(albumId);

        setAdapter();
    }

    private void setView() {
        mBackgroundView = findViewById(R.id.background);
        mThumbnailView = findViewById(R.id.thumbnail);
        mListView = findViewById(R.id.list);
    }

    private void setAdapter() {
        AlbumTrackListAdapter adapter = new AlbumTrackListAdapter(mTrackList, this);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mListView.setLayoutManager(layout);
        mListView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        mListView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onClickItem(int position) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.includeMiniPlayer);
        if (fragment instanceof MiniPlayerFragment) {
            ((MiniPlayerFragment) fragment).start(mTrackList, position);
        }
    }

    @Override
    public void onClickMenu(View view, Track track) {
        TrackMenu trackMenu = new TrackMenu(this, view, track);
        trackMenu.show();
    }
}
