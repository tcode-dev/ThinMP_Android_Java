package tokyo.tkw.thinmp.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
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
    private ListView mListView;

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
        setListener();
    }

    private void setView() {
        mThumbnailView = findViewById(R.id.thumbnail);
        mArtistNameView = findViewById(R.id.artistName);
        mListView = findViewById(R.id.list);
    }

    private void setAdapter() {
        ArtistAlbumListAdapter adapter = new ArtistAlbumListAdapter(this, R.layout.artist_album_list_item, mAlbumIdList);
        mListView.setAdapter(adapter);
    }

    private void setListener() {
        mListView.setOnItemClickListener(new AlbumClickListener());
    }

    /**
     *
     */
    private class AlbumClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Album album = mAlbumIdList.get(position);

            Intent intent = new Intent(ArtistActivity.this, AlbumActivity.class);
            intent.putExtra("album_id", album.getId());
            startActivity(intent);
        }
    }
}
