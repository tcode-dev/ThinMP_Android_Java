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
import tokyo.tkw.thinmp.artist.Artist;
import tokyo.tkw.thinmp.dto.FavoriteArtistsEditDto;
import tokyo.tkw.thinmp.epoxy.controller.FavoriteArtistsEditController;
import tokyo.tkw.thinmp.epoxy.model.ArtistEditModel;
import tokyo.tkw.thinmp.listener.CancelClickListener;
import tokyo.tkw.thinmp.logic.FavoriteArtistsEditLogic;
import tokyo.tkw.thinmp.register.edit.FavoriteArtistEditor;

public class FavoriteArtistsEditActivity extends BaseActivity {
    private RecyclerView listView;
    private FavoriteArtistsEditDto dto;
    private FavoriteArtistsEditController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite_artists_edit);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        listView = findViewById(R.id.list);
        Button applyView = findViewById(R.id.apply);
        Button cancelView = findViewById(R.id.cancel);

        // logic
        FavoriteArtistsEditLogic logic = FavoriteArtistsEditLogic.createInstance(this);

        // dto
        dto = logic.createDto();

        // controller
        controller = new FavoriteArtistsEditController();
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
        FavoriteArtistEditor favoriteArtistEditor = FavoriteArtistEditor.createInstance();
        List<String> artistIdList = Stream.of(dto.artistList).map(Artist::getId).collect(Collectors.toList());
        favoriteArtistEditor.update(artistIdList);
    }

    private void setListEvent() {
        EpoxyTouchHelper.initDragging(controller)
                .withRecyclerView(listView)
                .forVerticalList()
                .withTarget(ArtistEditModel.class)
                .andCallbacks(new EpoxyTouchHelper.DragCallbacks<ArtistEditModel>() {

                    @Override
                    public void onModelMoved(int fromPosition, int toPosition, ArtistEditModel modelBeingMoved,
                                             View itemView) {
                        dto.artistList.add(toPosition, dto.artistList.remove(fromPosition));
                    }
                });

        EpoxyTouchHelper.initSwiping(listView)
                .leftAndRight()
                .withTarget(ArtistEditModel.class)
                .andCallbacks(new EpoxyTouchHelper.SwipeCallbacks<ArtistEditModel>() {

                    @Override
                    public void onSwipeCompleted(ArtistEditModel model, View itemView, int position, int direction) {
                        dto.artistList.remove(position);
                        controller.setData(dto);
                    }
                });
    }
}
