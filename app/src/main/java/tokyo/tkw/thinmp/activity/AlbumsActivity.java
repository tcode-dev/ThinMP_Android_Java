package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.AlbumsDto;
import tokyo.tkw.thinmp.epoxy.controller.AlbumsController;
import tokyo.tkw.thinmp.logic.AlbumsLogic;

public class AlbumsActivity extends BaseActivity {
    AlbumsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_albums);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView listView = findViewById(R.id.list);

        // logic
        AlbumsLogic logic = AlbumsLogic.createInstance(this);

        // dto
        AlbumsDto dto = logic.createDto();

        // controller
        controller = new AlbumsController();
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

        screenUpdate();
    }

    public void screenUpdate() {
        // logic
        AlbumsLogic logic = AlbumsLogic.createInstance(this);

        // controller
        controller.setData(logic.createDto());
    }
}
