package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.TrackListAdapter;
import tokyo.tkw.thinmp.fragment.MiniPlayerFragment;
import tokyo.tkw.thinmp.menu.TrackMenu;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.TracksContentProvider;

public class TracksActivity extends AppCompatActivity {
    private ArrayList<Track> mTrackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        initList();
    }

    private void initList() {
        RecyclerView listView = findViewById(R.id.main);

        TracksContentProvider tracksContentProvider = new TracksContentProvider(this);
        mTrackList = tracksContentProvider.getList();

        TrackListAdapter adapter = new TrackListAdapter(mTrackList,
                trackListItemClickListener(this));
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);

        // 区切り線の描画
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                this, new LinearLayoutManager(this).getOrientation());
        listView.addItemDecoration(dividerItemDecoration);
    }

    private TrackListAdapter.OnTrackListItemClickListener trackListItemClickListener(Context context) {
        return new TrackListAdapter.OnTrackListItemClickListener() {

            @Override
            public void onClickItem(int position) {
                Fragment fragment =
                        getSupportFragmentManager().findFragmentById(R.id.includeMiniPlayer);
                if (fragment instanceof MiniPlayerFragment) {
                    ((MiniPlayerFragment) fragment).start(mTrackList, position);
                }
            }

            @Override
            public void onClickMenu(View view, Track track) {
                TrackMenu trackMenu = new TrackMenu(context, view, track);
                trackMenu.show();
            }
        };
    }
}
