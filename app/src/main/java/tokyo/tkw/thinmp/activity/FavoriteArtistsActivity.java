package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.FavoriteArtistsController;
import tokyo.tkw.thinmp.listener.ScreenUpdateListener;
import tokyo.tkw.thinmp.logic.FavoriteArtistsLogic;

public class FavoriteArtistsActivity extends BaseActivity implements ScreenUpdateListener {
    private FavoriteArtistsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite_artists);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView listView = findViewById(R.id.list);

        // logic
        FavoriteArtistsLogic logic = FavoriteArtistsLogic.createInstance(this);

        // controller
        controller = new FavoriteArtistsController();
        controller.setData(logic.createDto());
        listView.setAdapter(controller.getAdapter());

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        screenUpdate();
    }

    @Override
    public void screenUpdate() {
        FavoriteArtistsLogic logic = FavoriteArtistsLogic.createInstance(this);
        controller.setData(logic.createDto());
    }
}
