package tokyo.tkw.thinmp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.ArtistAlbumListAdapter;
import tokyo.tkw.thinmp.adapter.ArtistTrackListAdapter;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.plugin.RSBlurProcessor;
import tokyo.tkw.thinmp.provider.ThumbnailProvider;
import tokyo.tkw.thinmp.util.ViewUtil;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class ArtistDetailActivity extends AppCompatActivity {
    private ArtistTrackListAdapter.OnTrackListItemClickListener mTrackListListener =
            new ArtistTrackListAdapter.OnTrackListItemClickListener() {

        @Override
        public void onClickItem(int position) {

        }

        @Override
        public void onClickMenu(View view, Track track) {

        }
    };

    private ArtistActivityListener mArtistActivityListener = new ArtistActivityListener() {

        @Override
        public Bitmap getThumbnail(String id) {
            return mThumbnailProvider.getThumbnail(id);
        }
    };

    private ThumbnailProvider mThumbnailProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        String artistId = getIntent().getStringExtra("artistId");
        Artist artist = MusicList.getArtist(artistId);

        // アルバムのサムネイルのviewの幅を取得
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Float padding = ViewUtil.dpToDimensionPx(this,60);
        Float width = (dm.widthPixels - padding) / 2;

        mThumbnailProvider = ThumbnailProvider.createAlbumThumbnailProvider(this, width);

        // サムネイル
        ImageView thumbnailView = findViewById(R.id.thumbnail);
        Bitmap thumbnailBitmap = mThumbnailProvider.getThumbnail(artist.getThumbnailIdList());
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

        // アルバム一覧
        ArrayList<Album> albumIdList = MusicList.getArtistAlbumList(artistId);
        RecyclerView albumListView = findViewById(R.id.albumList);
        ArtistAlbumListAdapter adapter = new ArtistAlbumListAdapter(this, albumIdList, mArtistActivityListener);
        GridLayoutManager layout = new GridLayoutManager(this, 2);
        albumListView.setLayoutManager(layout);
        albumListView.setAdapter(adapter);

        // 曲一覧
        ArrayList<Track> trackList = MusicList.getArtistTrackList(artistId);
        RecyclerView trackListView = findViewById(R.id.trackList);
        ArtistTrackListAdapter artistTrackListAdapter = new ArtistTrackListAdapter(trackList,
                mTrackListListener, mArtistActivityListener);
        LinearLayoutManager layout2 = new LinearLayoutManager(this);
        trackListView.setLayoutManager(layout2);
        trackListView.setAdapter(artistTrackListAdapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        trackListView.addItemDecoration(dividerItemDecoration);
    }

    /**
     * interface
     */
    public interface ArtistActivityListener {
        Bitmap getThumbnail(String id);
    }
}
