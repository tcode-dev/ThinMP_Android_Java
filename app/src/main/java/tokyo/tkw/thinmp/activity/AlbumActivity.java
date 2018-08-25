package tokyo.tkw.thinmp.activity;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.adapter.AlbumTrackListAdapter;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.fragment.PlayerFragment;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.plugin.RSBlurProcessor;
import tokyo.tkw.thinmp.util.ThumbnailController;
import tokyo.tkw.thinmp.music.Track;

public class AlbumActivity extends AppCompatActivity implements PlayerFragment.OnFragmentInteractionListener {
    private ImageView mBackgroundView;
    private ImageView mThumbnailView;
    private TextView mAlbumNameView;
    private TextView mArtistNameView;
    private RecyclerView mListView;

    private ArrayList<Track> mTrackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        setView();

        String albumId = getIntent().getStringExtra("albumId");
        Album album = MusicList.getAlbum(albumId);

        //アルバム名
        mAlbumNameView.setText(album.getName());

        //アーティスト名
        mArtistNameView.setText(album.getArtistName());

        //サムネイル
        Bitmap thumbnailBitmap = new ThumbnailController(this).getThumbnail(album.getThumbnailId());
        mThumbnailView.setImageBitmap(thumbnailBitmap);

        //背景画像(同じ画像はキャッシュしているので、サムネイルもぼかしが掛からないようにnewして取得)
        Bitmap backgroundBitmap = new ThumbnailController(this).getThumbnail(album.getThumbnailId());
        RenderScript rs = RenderScript.create(this);
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs);
        Bitmap blurBitMap = rsBlurProcessor.blur(backgroundBitmap, 20f,  3);
        mBackgroundView.setImageBitmap(blurBitMap);

        //曲一覧
        mTrackList = MusicList.getAlbumTrackList(albumId);

        setAdapter();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        final Rect rect = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);

        mThumbnailView.setPadding(0, rect.top + 100, 0, 100);
    }
    private void setView() {
        mBackgroundView = findViewById(R.id.background);
        mThumbnailView = findViewById(R.id.thumbnail);
        mAlbumNameView = findViewById(R.id.primaryText);
        mArtistNameView = findViewById(R.id.artistName);
        mListView = findViewById(R.id.list);
    }

    private void setAdapter() {
        AlbumTrackListAdapter adapter = new AlbumTrackListAdapter(this, mTrackList);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        mListView.setLayoutManager(layout);
        mListView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        mListView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
