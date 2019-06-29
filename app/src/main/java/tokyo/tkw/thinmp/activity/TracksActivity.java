package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.TrackListAdapter;
import tokyo.tkw.thinmp.listener.TrackClickListener;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.AllTracksContentProvider;

public class TracksActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        RecyclerView listView = findViewById(R.id.list);

        AllTracksContentProvider allTracksContentProvider = new AllTracksContentProvider(this);
        ArrayList<Track> trackList = allTracksContentProvider.getList();

        TrackListAdapter adapter = new TrackListAdapter(trackList,
                new TrackClickListener(trackList));

        LinearLayoutManager layout = new LinearLayoutManager(this);

        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);
    }
}
