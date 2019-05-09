package tokyo.tkw.thinmp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumListAdapter;
import tokyo.tkw.thinmp.music.MusicList;

public class AlbumsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        RecyclerView listView = findViewById(R.id.list);

        AlbumListAdapter adapter = new AlbumListAdapter(this, MusicList.getAlbumList());
        GridLayoutManager layout = new GridLayoutManager(this, 2);
        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);
    }
}
