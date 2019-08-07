package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.epoxy.EpoxyTouchHelper;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.MainEditDto;
import tokyo.tkw.thinmp.epoxy.controller.MainEditController;
import tokyo.tkw.thinmp.epoxy.model.ShortcutModel;
import tokyo.tkw.thinmp.logic.MainEditLogic;
import tokyo.tkw.thinmp.shortcut.Shortcut;

public class MainEditActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main_edit);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView listView = findViewById(R.id.list);

        // logic
        MainEditLogic logic = MainEditLogic.createInstance(this);

        // dto
        MainEditDto dto = logic.createDto();

        // controller
        MainEditController controller = new MainEditController();
        controller.setData(dto);
        listView.setAdapter(controller.getAdapter());

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        EpoxyTouchHelper.initDragging(controller)
                .withRecyclerView(listView)
                .forVerticalList()
                .withTarget(ShortcutModel.class)
                .andCallbacks(new EpoxyTouchHelper.DragCallbacks<ShortcutModel>() {

                    @Override
                    public void onModelMoved(int fromPosition, int toPosition, ShortcutModel modelBeingMoved,
                                             View itemView) {
                        int shortcutFromPosition = fromPosition - dto.shortcutStartIndex;
                        int shortcutToPosition = toPosition - dto.shortcutStartIndex;

                        dto.shortcutList.add(shortcutToPosition, dto.shortcutList.remove(shortcutFromPosition));
                    }
                });
    }
}
