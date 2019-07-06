package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.TrackListAdapter;
import tokyo.tkw.thinmp.listener.TrackClickListener;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.music.TrackCollection;

public class TracksActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tracks);

        initWithPermissionCheck();
    }

    @Override
    protected void init() {
        RecyclerView listView = findViewById(R.id.list);

        TrackCollection trackCollection = TrackCollection.createAllTrackCollectionInstance(this);
        List<Track> trackList = trackCollection.getList();

        TrackListAdapter adapter = new TrackListAdapter(trackList,
                new TrackClickListener(trackList));

        LinearLayoutManager layout = new LinearLayoutManager(this);

        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);
    }
}
