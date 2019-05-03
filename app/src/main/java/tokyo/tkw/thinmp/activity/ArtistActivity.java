package tokyo.tkw.thinmp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.ArtistAlbumListAdapter;
import tokyo.tkw.thinmp.adapter.ArtistTrackListAdapter;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.plugin.RSBlurProcessor;
import tokyo.tkw.thinmp.view.ResponsiveTextView;

public class ArtistActivity extends AppCompatActivity {
    private ArtistTrackListAdapter.OnTrackListItemClickListener mTrackListListener =
            new ArtistTrackListAdapter.OnTrackListItemClickListener() {

        @Override
        public void onClickItem(int position) {

        }

        @Override
        public void onClickMenu(View view, Track track) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        String artistId = getIntent().getStringExtra("artistId");
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

        // アルバム一覧
        ArrayList<Album> albumIdList = MusicList.getArtistAlbumList(artistId);
        RecyclerView albumListView = findViewById(R.id.albumList);
        ArtistAlbumListAdapter adapter = new ArtistAlbumListAdapter(this, albumIdList);
        LinearLayoutManager layout = new GridLayoutManager(this, 2);
        albumListView.setLayoutManager(layout);
        albumListView.setAdapter(adapter);

        // 曲一覧
        ArrayList<Track> trackList = MusicList.getArtistTrackList(artistId);
        RecyclerView trackListView = findViewById(R.id.trackList);
        ArtistTrackListAdapter artistTrackListAdapter = new ArtistTrackListAdapter(trackList,
                mTrackListListener);
        LinearLayoutManager layout2 = new LinearLayoutManager(this);
        trackListView.setLayoutManager(layout2);
        trackListView.setAdapter(artistTrackListAdapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        trackListView.addItemDecoration(dividerItemDecoration);
    }
}
