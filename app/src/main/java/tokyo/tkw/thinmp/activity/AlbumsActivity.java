package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumListAdapter;
import tokyo.tkw.thinmp.music.AlbumList;

public class AlbumsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        RecyclerView listView = findViewById(R.id.list);

        AlbumList albumList = new AlbumList(this);
        AlbumListAdapter adapter = new AlbumListAdapter(this, albumList.getAllAlbumList());
        GridLayoutManager layout = new GridLayoutManager(this, 2);
        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);
    }
}
