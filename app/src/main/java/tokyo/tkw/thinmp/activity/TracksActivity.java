package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.TracksDto;
import tokyo.tkw.thinmp.epoxy.controller.TracksController;
import tokyo.tkw.thinmp.logic.TracksLogic;

public class TracksActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tracks);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        TextView titleView = findViewById(R.id.title);
        RecyclerView listView = findViewById(R.id.list);

        // logic
        TracksLogic logic = TracksLogic.createInstance(this);

        // dto
        TracksDto dto = logic.createDto();

        // ページ名
        titleView.setText(dto.title);

        // controller
        TracksController controller = new TracksController();
        controller.setData(dto);
        listView.setAdapter(controller.getAdapter());

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);
    }
}
