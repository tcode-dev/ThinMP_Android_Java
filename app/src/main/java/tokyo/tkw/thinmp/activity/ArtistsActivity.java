package tokyo.tkw.thinmp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.ArtistListAdapter;
import tokyo.tkw.thinmp.music.MusicList;

public class ArtistsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        initList();
    }

    private void initList() {
        RecyclerView listView = findViewById(R.id.list);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        ArtistListAdapter adapter = new ArtistListAdapter(this, MusicList.getArtistList());
        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        listView.addItemDecoration(dividerItemDecoration);
    }
}
