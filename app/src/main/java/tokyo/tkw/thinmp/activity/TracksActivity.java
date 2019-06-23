package tokyo.tkw.thinmp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.TrackListAdapter;
import tokyo.tkw.thinmp.listener.TrackClickListener;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.provider.AllTracksContentProvider;

public class TracksActivity extends AppCompatActivity {
    private ArrayList<Track> mTrackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        initList();
    }

    private void initList() {
        RecyclerView listView = findViewById(R.id.list);

        AllTracksContentProvider allTracksContentProvider = new AllTracksContentProvider(this);
        mTrackList = allTracksContentProvider.getList();

        TrackListAdapter adapter = new TrackListAdapter(mTrackList,
                new TrackClickListener(mTrackList));
        LinearLayoutManager layout = new LinearLayoutManager(this);
        listView.setLayoutManager(layout);
        listView.setAdapter(adapter);
    }
}
