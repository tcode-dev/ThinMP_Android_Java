package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.FavoriteSongsDto;
import tokyo.tkw.thinmp.epoxy.controller.FavoriteSongsController;
import tokyo.tkw.thinmp.listener.CommonHeaderMenuClickListener;
import tokyo.tkw.thinmp.listener.ScreenUpdateListener;
import tokyo.tkw.thinmp.logic.FavoriteSongsLogic;

public class FavoriteSongsActivity extends BaseActivity implements ScreenUpdateListener {
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
        TextView titleView = findViewById(R.id.title);
        RecyclerView listView = findViewById(R.id.list);
        ImageView menuView = findViewById(R.id.menu);

        // logic
        FavoriteSongsLogic logic = FavoriteSongsLogic.createInstance(this);

        // dto
        FavoriteSongsDto dto = logic.createDto();

        // ページ名
        titleView.setText(dto.title);

        // controller
        controller = new FavoriteSongsController();
        controller.setData(dto);
        listView.setAdapter(controller.getAdapter());

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // event
        menuView.setOnClickListener(new CommonHeaderMenuClickListener(FavoriteSongsEditActivity.class));
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        screenUpdate();
    }

    @Override
    public void screenUpdate() {
        FavoriteSongsLogic logic = FavoriteSongsLogic.createInstance(this);
        controller.setData(logic.createDto());
    }
}
