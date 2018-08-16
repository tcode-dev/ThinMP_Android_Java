package tokyo.tkw.thinmp.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.model.Album;
import tokyo.tkw.thinmp.model.Artist;
import tokyo.tkw.thinmp.adapter.ArtistAlbumListAdapter;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.util.ThumbnailController;

public class ArtistActivity extends AppCompatActivity {
    private ImageView mThumbnailView;
    private TextView mArtistNameView;
    private RecyclerView mListView;

    private ArrayList<Album> mAlbumIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        String artistId = getIntent().getStringExtra("artist_id");

        Artist artist = MusicList.getArtist(artistId);

        setView();

        //アーティスト名
        String artistName = artist.getName();
        setTitle(artistName);
        mArtistNameView.setText(artistName);

        //サムネイル
        ThumbnailController thumbnailController = new ThumbnailController(this);
        Bitmap thumbnail = thumbnailController.getThumbnail(artist.getThumbnailId());
        mThumbnailView.setImageBitmap(thumbnail);

        //アルバム一覧
        mAlbumIdList = MusicList.getArtistAlbumList(artistId);

        setAdapter();
    }

    private void setView() {
        mThumbnailView = findViewById(R.id.thumbnail);
        mArtistNameView = findViewById(R.id.artistName);
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
