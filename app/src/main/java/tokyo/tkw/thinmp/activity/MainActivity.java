package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.epoxy.controller.MainController;
import tokyo.tkw.thinmp.logic.MainLogic;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView listView = findViewById(R.id.list);

        // logic
        MainLogic logic = MainLogic.createInstance(this);

        // controller
        MainController controller = new MainController();
        controller.setData(logic.createDto());
        listView.setAdapter(controller.getAdapter());

        // layout
        GridLayoutManager layout = new GridLayoutManager(this, 2);
        controller.setSpanCount(2);
        layout.setSpanSizeLookup(controller.getSpanSizeLookup());
        listView.setLayoutManager(layout);
    }
}