package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.ArtistsController;
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
        ArtistsController controller = new ArtistsController();
        listView.setLayoutManager(layout);
        listView.setAdapter(controller.getAdapter());

        ArtistsController.Data data = new ArtistsController.Data();
        data.title = getResources().getString(R.string.artists);
        data.artistList = MusicList.getArtistList();
        controller.setData(data, this);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        listView.addItemDecoration(dividerItemDecoration);
    }
}
