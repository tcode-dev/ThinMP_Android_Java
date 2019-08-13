package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.ArtistsDto;
import tokyo.tkw.thinmp.epoxy.controller.ArtistsController;
import tokyo.tkw.thinmp.logic.ArtistsLogic;

public class ArtistsActivity extends BaseActivity {
    private ArtistsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artists);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        TextView titleView = findViewById(R.id.title);
        RecyclerView listView = findViewById(R.id.list);

        // logic
        ArtistsLogic logic = ArtistsLogic.createInstance(this);

        // dto
        ArtistsDto dto = logic.createDto();

        // ページ名
        titleView.setText(dto.title);

        // controller
        controller = new ArtistsController();
        controller.setData(dto);
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
