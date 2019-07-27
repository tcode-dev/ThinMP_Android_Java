package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.PlaylistsDto;
import tokyo.tkw.thinmp.epoxy.controller.PlaylistsController;
import tokyo.tkw.thinmp.logic.PlaylistsLogic;

public class PlaylistsActivity extends BaseActivity {
    private PlaylistsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlists);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView listView = findViewById(R.id.list);

        // logic
        PlaylistsLogic logic = PlaylistsLogic.createInstance(this);

        // dto
        PlaylistsDto dto = logic.createDto();

        // controller
        controller = new PlaylistsController();
        controller.setData(dto);
        listView.setAdapter(controller.getAdapter());

        // layout
        GridLayoutManager layout = new GridLayoutManager(this, dto.layoutSpanSize);
        controller.setSpanCount(dto.layoutSpanSize);
        layout.setSpanSizeLookup(controller.getSpanSizeLookup());
        listView.setLayoutManager(layout);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // update
        PlaylistsLogic logic = PlaylistsLogic.createInstance(this);
        controller.setData(logic.createDto());
    }
}
