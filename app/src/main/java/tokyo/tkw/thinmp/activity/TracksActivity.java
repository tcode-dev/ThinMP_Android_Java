package tokyo.tkw.thinmp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.TrackListAdapter;
import tokyo.tkw.thinmp.fragment.MiniPlayerFragment;
import tokyo.tkw.thinmp.menu.TrackMenu;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;

public class TracksActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        initList();
    }

    private void initList() {
        RecyclerView listView = findViewById(R.id.main);

        TrackListAdapter adapter = new TrackListAdapter(MusicList.getTrackList(),
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
                    ((MiniPlayerFragment) fragment).start(MusicList.getTrackList(), position);
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
