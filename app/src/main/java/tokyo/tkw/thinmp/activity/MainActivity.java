package tokyo.tkw.thinmp.activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.ViewPagerAdapter;
import tokyo.tkw.thinmp.fragment.FavoriteSongListFragment;
import tokyo.tkw.thinmp.fragment.MiniPlayerFragment;
import tokyo.tkw.thinmp.fragment.TrackListFragment;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.music.Track;

public class MainActivity
        extends AppCompatActivity
        implements
        TrackListFragment.OnFragmentInteractionListener,
        FavoriteSongListFragment.OnFragmentInteractionListener {

    private final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        MusicList.setInstance(this);

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onStartClick(ArrayList<Track> trackList, int position) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.includeMiniPlayer);
        if (fragment instanceof MiniPlayerFragment) {
            ((MiniPlayerFragment) fragment).start(trackList, position);
        }
    }

    /**
     * ユーザーにパーミッション権限を要求した承認結果を受け取る
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    recreate();
                }

                return;
            }
        }
    }
}