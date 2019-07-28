package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.ArtistsController;
import tokyo.tkw.thinmp.logic.ArtistsLogic;

public class ArtistsActivity extends BaseActivity {
    ArtistsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artists);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView listView = findViewById(R.id.list);

        // logic
        ArtistsLogic logic = ArtistsLogic.createInstance(this);

        // controller
        controller = new ArtistsController();
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

    private void screenUpdate() {
        ArtistsLogic logic = ArtistsLogic.createInstance(this);
        controller.setData(logic.createDto());
    }
}
