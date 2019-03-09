package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.ArtistAlbumListAdapter;
import tokyo.tkw.thinmp.music.Album;
import tokyo.tkw.thinmp.music.Artist;
import tokyo.tkw.thinmp.music.MusicList;

public class ArtistActivity extends AppCompatActivity {
    private TextView mArtistNameView;
    private RecyclerView mListView;

    private ArrayList<Album> mAlbumIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        String artistId = getIntent().getStringExtra("artistId");
        Artist artist = MusicList.getArtist(artistId);

        setView();

        //アーティスト名
        mArtistNameView.setText(artist.getName());

        //アルバム一覧
        mAlbumIdList = MusicList.getArtistAlbumList(artistId);

        setAdapter();
    }

    private void setView() {
        mArtistNameView = findViewById(R.id.artistName);
        mListView = findViewById(R.id.list);
    }

    private void setAdapter() {
        ArtistAlbumListAdapter adapter = new ArtistAlbumListAdapter(this, mAlbumIdList);
        LinearLayoutManager layout = new GridLayoutManager(this, 2);
        mListView.setLayoutManager(layout);
        mListView.setAdapter(adapter);
    }
}
