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
import tokyo.tkw.thinmp.dto.PlaylistsEditDto;
import tokyo.tkw.thinmp.epoxy.controller.PlaylistEditController;
import tokyo.tkw.thinmp.epoxy.model.PlaylistEditModel;
import tokyo.tkw.thinmp.listener.CancelClickListener;
import tokyo.tkw.thinmp.logic.PlaylistsEditLogic;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.register.edit.PlaylistsEditor;

public class PlaylistsEditActivity extends BaseActivity {
    private RecyclerView listView;
    private PlaylistsEditDto dto;
    private PlaylistEditController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlists_edit);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        listView = findViewById(R.id.list);
        Button applyView = findViewById(R.id.apply);
        Button cancelView = findViewById(R.id.cancel);

        // logic
        PlaylistsEditLogic logic = PlaylistsEditLogic.createInstance(this);

        // dto
        dto = logic.createDto();

        // controller
        controller = new PlaylistEditController();
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
        PlaylistsEditor playlistsEditor = PlaylistsEditor.createInstance();

        List<String> toPlaylistIdList = Stream.of(dto.playlists).map(Playlist::getId).collect(Collectors.toList());

        playlistsEditor.update(dto.playlistIdList, toPlaylistIdList);
    }

    private void setListEvent() {
        EpoxyTouchHelper.initDragging(controller)
                .withRecyclerView(listView)
                .forVerticalList()
                .withTarget(PlaylistEditModel.class)
                .andCallbacks(new EpoxyTouchHelper.DragCallbacks<PlaylistEditModel>() {

                    @Override
                    public void onModelMoved(int fromPosition, int toPosition, PlaylistEditModel modelBeingMoved,
                                             View itemView) {
                        dto.playlists.add(toPosition, dto.playlists.remove(fromPosition));
                    }
                });

        EpoxyTouchHelper.initSwiping(listView)
                .leftAndRight()
                .withTarget(PlaylistEditModel.class)
                .andCallbacks(new EpoxyTouchHelper.SwipeCallbacks<PlaylistEditModel>() {

                    @Override
                    public void onSwipeCompleted(PlaylistEditModel model, View itemView, int position, int direction) {
                        dto.playlists.remove(position);
                        controller.setData(dto);
                    }
                });
    }
}
