package tokyo.tkw.thinmp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import tokyo.tkw.thinmp.view.ResponsiveCircleImageView;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

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
        Bitmap thumbnailBitmap = artist.getThumbnail();
        mThumbnailView.setImageBitmap(thumbnailBitmap);

        //背景画像
        Bitmap backgroundBitmap = thumbnailBitmap.copy(Bitmap.Config.ARGB_8888, true);
        RenderScript rs = RenderScript.create(this);
        RSBlurProcessor rsBlurProcessor = new RSBlurProcessor(rs);
        Bitmap blurBitMap = rsBlurProcessor.blur(backgroundBitmap, 20f, 3);
        mBackgroundView.setImageBitmap(blurBitMap);

        ResponsiveTextView titleView = findViewById(R.id.title);
        titleView.setText(artist.getName());

        //アルバム一覧
        mAlbumIdList = MusicList.getArtistAlbumList(artistId);

        setAdapter();
    }

    private void setView() {
        mBackgroundView = findViewById(R.id.background);
        mThumbnailView = findViewById(R.id.thumbnail);
        mListView = findViewById(R.id.list);
    }

    private void setAdapter() {
        ArtistAlbumListAdapter adapter = new ArtistAlbumListAdapter(this, mAlbumIdList);
        LinearLayoutManager layout = new GridLayoutManager(this, 2);
        mListView.setLayoutManager(layout);
        mListView.setAdapter(adapter);
    }
}
