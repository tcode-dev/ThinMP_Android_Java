package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.MainDto;
import tokyo.tkw.thinmp.epoxy.controller.MainController;
import tokyo.tkw.thinmp.logic.MainLogic;

public class MainActivity extends BaseActivity {
    private MainController controller;

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

        // dto
        MainDto dto = logic.createDto();

        // controller
        controller = new MainController();
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


    private void screenUpdate() {
        MainLogic logic = MainLogic.createInstance(this);
        controller.setData(logic.createDto());
    }
}