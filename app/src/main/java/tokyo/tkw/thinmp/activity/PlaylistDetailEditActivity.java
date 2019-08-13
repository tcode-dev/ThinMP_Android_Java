package tokyo.tkw.thinmp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.epoxy.EpoxyTouchHelper;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.PlaylistDetailEditDto;
import tokyo.tkw.thinmp.epoxy.controller.PlaylistDetailEditController;
import tokyo.tkw.thinmp.epoxy.model.TrackEditModel;
import tokyo.tkw.thinmp.listener.CancelClickListener;
import tokyo.tkw.thinmp.logic.PlaylistDetailEditLogic;
import tokyo.tkw.thinmp.playlist.Playlist;
import tokyo.tkw.thinmp.register.edit.PlaylistEditor;
import tokyo.tkw.thinmp.track.Track;

public class PlaylistDetailEditActivity extends BaseActivity {
    private String playlistId;
    private RecyclerView listView;
    private PlaylistDetailEditDto dto;
    private PlaylistDetailEditController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_playlist_detail_edit);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // playlistId
        playlistId = getIntent().getStringExtra(Playlist.PLAYLIST_ID);

        // logic
        PlaylistDetailEditLogic logic = PlaylistDetailEditLogic.createInstance(this, playlistId);

        // dto
        logic.createDto().ifPresentOrElse(this::showDetail, this::notFound);
    }

    private void showDetail(PlaylistDetailEditDto playlistDetailEditDto) {
        // view
        listView = findViewById(R.id.list);
        Button applyView = findViewById(R.id.apply);
        Button cancelView = findViewById(R.id.cancel);

        // dto
        dto = playlistDetailEditDto;

        // controller
        controller = new PlaylistDetailEditController();
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

    private void notFound() {
        setContentView(R.layout.activity_not_found);

        TextView textView = findViewById(R.id.text);
        textView.setText(getString(R.string.playlist_not_found));
    }

    private View.OnClickListener createApplyClickListener() {
        return view -> {
            apply();
            finish();
        };
    }

    private void apply() {
        PlaylistEditor playlistEditor = PlaylistEditor.createInstance();
        EditText playlistNameView = findViewById(R.id.playlistName);
        String name = playlistNameView.getText().toString();
        List<String> toTrackIdList = Stream.of(dto.trackList).map(Track::getId).collect(Collectors.toList());

        playlistEditor.update(playlistId, name, dto.trackIdList, toTrackIdList);
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
                        int actualFromPosition = fromPosition - dto.startPosition;
                        int actualToPosition = toPosition - dto.startPosition;

                        dto.trackList.add(actualToPosition, dto.trackList.remove(actualFromPosition));
                    }
                });

        EpoxyTouchHelper.initSwiping(listView)
                .leftAndRight()
                .withTarget(TrackEditModel.class)
                .andCallbacks(new EpoxyTouchHelper.SwipeCallbacks<TrackEditModel>() {

                    @Override
                    public void onSwipeCompleted(TrackEditModel model, View itemView, int position,
                                                 int direction) {
                        int actualPosition = position - dto.startPosition;
                        dto.trackList.remove(actualPosition);
                        controller.setData(dto);
                    }
                });
    }
}
