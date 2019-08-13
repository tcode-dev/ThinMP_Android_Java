package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.epoxy.EpoxyTouchHelper;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.FavoriteSongsEditDto;
import tokyo.tkw.thinmp.epoxy.controller.FavoriteSongsEditController;
import tokyo.tkw.thinmp.epoxy.model.TrackEditModel;
import tokyo.tkw.thinmp.listener.CancelClickListener;
import tokyo.tkw.thinmp.logic.FavoriteSongsEditLogic;
import tokyo.tkw.thinmp.register.edit.FavoriteSongEditor;
import tokyo.tkw.thinmp.track.Track;

public class FavoriteSongsEditActivity extends BaseActivity {
    private RecyclerView listView;
    private FavoriteSongsEditDto dto;
    private FavoriteSongsEditController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite_songs_edit);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        listView = findViewById(R.id.list);
        Button applyView = findViewById(R.id.apply);
        Button cancelView = findViewById(R.id.cancel);

        // logic
        FavoriteSongsEditLogic logic = FavoriteSongsEditLogic.createInstance(this);

        // dto
        dto = logic.createDto();

        // controller
        controller = new FavoriteSongsEditController();
        controller.setData(dto);
        listView.setAdapter(controller.getAdapter());

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // event
        applyView.setOnClickListener(createApplyClickListener());
        cancelView.setOnClickListener(new CancelClickListener());
        setListEvent();
    }

    private View.OnClickListener createApplyClickListener() {
        return view -> {
            apply();
            finish();
        };
    }

    private void apply() {
        FavoriteSongEditor favoriteSongEditor = FavoriteSongEditor.createInstance();

        List<String> trackIdList = Stream.of(dto.trackList).map(Track::getId).collect(Collectors.toList());

        favoriteSongEditor.update(trackIdList);
    }

    private void setListEvent() {
        EpoxyTouchHelper.initDragging(controller)
                .withRecyclerView(listView)
                .forVerticalList()
                .withTarget(TrackEditModel.class)
                .andCallbacks(new EpoxyTouchHelper.DragCallbacks<TrackEditModel>() {

                    @Override
                    public void onModelMoved(int fromPosition, int toPosition, TrackEditModel modelBeingMoved,
                                             View itemView) {
                        dto.trackList.add(toPosition, dto.trackList.remove(fromPosition));
                    }
                });

        EpoxyTouchHelper.initSwiping(listView)
                .leftAndRight()
                .withTarget(TrackEditModel.class)
                .andCallbacks(new EpoxyTouchHelper.SwipeCallbacks<TrackEditModel>() {

                    @Override
                    public void onSwipeCompleted(TrackEditModel model, View itemView, int position, int direction) {
                        dto.trackList.remove(position);
                        controller.setData(dto);
                    }
                });
    }
}
