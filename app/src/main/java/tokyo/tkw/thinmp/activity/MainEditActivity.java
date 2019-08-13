package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.epoxy.EpoxyTouchHelper;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.MainEditDto;
import tokyo.tkw.thinmp.epoxy.controller.MainEditController;
import tokyo.tkw.thinmp.epoxy.model.MainMenuEditModel;
import tokyo.tkw.thinmp.epoxy.model.ShortcutModel;
import tokyo.tkw.thinmp.listener.CancelClickListener;
import tokyo.tkw.thinmp.listener.MainEditApplyClickListener;
import tokyo.tkw.thinmp.logic.MainEditLogic;

public class MainEditActivity extends BaseActivity {
    private RecyclerView listView;
    private MainEditDto dto;
    private MainEditController controller;

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
        listView = findViewById(R.id.list);
        Button applyView = findViewById(R.id.apply);
        Button cancelView = findViewById(R.id.cancel);

        // logic
        MainEditLogic logic = MainEditLogic.createInstance(this);

        // dto
        dto = logic.createDto();

        // controller
        controller = new MainEditController();
        controller.setData(dto);
        listView.setAdapter(controller.getAdapter());

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // event
        setEvent();
        applyView.setOnClickListener(new MainEditApplyClickListener(dto.fromShortcutList, dto.shortcutList,
                dto.menuList, dto.stateMap));
        cancelView.setOnClickListener(new CancelClickListener());
    }

    private void setEvent() {
        setMenuEvent();
        setShortcutList();
    }

    private void setMenuEvent() {
        EpoxyTouchHelper.initDragging(controller)
                .withRecyclerView(listView)
                .forVerticalList()
                .withTarget(MainMenuEditModel.class)
                .andCallbacks(new EpoxyTouchHelper.DragCallbacks<MainMenuEditModel>() {

                    @Override
                    public void onModelMoved(int fromPosition, int toPosition, MainMenuEditModel modelBeingMoved,
                                             View itemView) {

                        int actualFromPosition = fromPosition - dto.menuStartPosition;
                        int actualToPosition = toPosition - dto.menuStartPosition;

                        dto.menuList.add(actualToPosition, dto.menuList.remove(actualFromPosition));
                    }
                });
    }

    private void setShortcutList() {
        EpoxyTouchHelper.initDragging(controller)
                .withRecyclerView(listView)
                .forVerticalList()
                .withTarget(ShortcutModel.class)
                .andCallbacks(new EpoxyTouchHelper.DragCallbacks<ShortcutModel>() {

                    @Override
                    public void onModelMoved(int fromPosition, int toPosition, ShortcutModel modelBeingMoved,
                                             View itemView) {
                        int actualFromPosition = fromPosition - dto.shortcutStartPosition;
                        int actualToPosition = toPosition - dto.shortcutStartPosition;

                        dto.shortcutList.add(actualToPosition, dto.shortcutList.remove(actualFromPosition));
                    }
                });

        EpoxyTouchHelper.initSwiping(listView)
                .leftAndRight()
                .withTarget(ShortcutModel.class)
                .andCallbacks(new EpoxyTouchHelper.SwipeCallbacks<ShortcutModel>() {

                    @Override
                    public void onSwipeCompleted(ShortcutModel model, View itemView, int position,
                                                 int direction) {
                        int actualPosition = position - dto.shortcutStartPosition;
                        dto.shortcutList.remove(actualPosition);
                        controller.setData(dto);
                    }
                });
    }
}
