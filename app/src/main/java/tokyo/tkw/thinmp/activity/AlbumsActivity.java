package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.AlbumsController;
import tokyo.tkw.thinmp.logic.AlbumsLogic;

public class AlbumsActivity extends BaseActivity {

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

        // controller
        AlbumsController controller = new AlbumsController();
        controller.setData(logic.createDto());
        listView.setAdapter(controller.getAdapter());

        // layout
        GridLayoutManager layout = new GridLayoutManager(this, 2);
        controller.setSpanCount(2);
        layout.setSpanSizeLookup(controller.getSpanSizeLookup());
        listView.setLayoutManager(layout);
    }
}
