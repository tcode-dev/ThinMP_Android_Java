package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.FavoriteSongListAdapter;
import tokyo.tkw.thinmp.dto.FavoriteSongsDto;
import tokyo.tkw.thinmp.listener.FavoriteSongClickListener;
import tokyo.tkw.thinmp.logic.FavoriteSongsLogic;

public class FavoriteSongsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_favorite_songs);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        // view
        RecyclerView listView = findViewById(R.id.list);
        Button editView = findViewById(R.id.edit);

        // logic
        FavoriteSongsLogic logic = FavoriteSongsLogic.createInstance(this);

        // dto
        FavoriteSongsDto dto = logic.createDto();

        // adapter
        FavoriteSongListAdapter adapter = new FavoriteSongListAdapter(
                dto.realmResults,
                dto.trackMap,
                new FavoriteSongClickListener()
        );
        listView.setAdapter(adapter);

        // layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);

        // event
        editView.setOnClickListener(createEditClickListener());
    }

    private View.OnClickListener createEditClickListener() {
        return view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, FavoriteSongsEditActivity.class);
            context.startActivity(intent);
        };
    }
}
