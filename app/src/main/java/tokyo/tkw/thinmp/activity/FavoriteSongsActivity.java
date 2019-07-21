package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.FavoriteSongsController;
import tokyo.tkw.thinmp.logic.FavoriteArtistsLogic;
import tokyo.tkw.thinmp.logic.FavoriteSongsLogic;

public class FavoriteSongsActivity extends BaseActivity {
    private FavoriteSongsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite_songs);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView listView = findViewById(R.id.list);

        // logic
        FavoriteSongsLogic logic = FavoriteSongsLogic.createInstance(this);

        // controller
        controller = new FavoriteSongsController();
        controller.setData(logic.createDto());
        listView.setAdapter(controller.getAdapter());

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // update
        FavoriteSongsLogic logic = FavoriteSongsLogic.createInstance(this);
        controller.setData(logic.createDto());
    }
}
