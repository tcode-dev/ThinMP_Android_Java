package tokyo.tkw.thinmp.activity;

import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;

import tokyo.tkw.thinmp.favorite.FavoriteRegister;
import tokyo.tkw.thinmp.fragment.FavoriteListFragment;
import tokyo.tkw.thinmp.fragment.MiniPlayerFragment;
import tokyo.tkw.thinmp.fragment.PlaylistFragment;
import tokyo.tkw.thinmp.fragment.TopFragment;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.ViewPagerAdapter;
import tokyo.tkw.thinmp.fragment.AlbumListFragment;
import tokyo.tkw.thinmp.fragment.ArtistListFragment;
import tokyo.tkw.thinmp.fragment.TrackListFragment;
import tokyo.tkw.thinmp.music.Track;
import tokyo.tkw.thinmp.util.ActivityUtil;

public class MainActivity
        extends AppCompatActivity
        implements
        ArtistListFragment.OnFragmentInteractionListener,
        AlbumListFragment.OnFragmentInteractionListener,
        TrackListFragment.OnFragmentInteractionListener,
        PlaylistFragment.OnFragmentInteractionListener,
        FavoriteListFragment.OnFragmentInteractionListener,
        TopFragment.OnFragmentInteractionListener,
        MiniPlayerFragment.OnFragmentInteractionListener {

    private final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MusicList.setInstance(this);
        FavoriteRegister.setInstance(this);

        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        setSize();
        setTitleBarPadding();
    }

    private void setSize() {
        final Rect rect = new Rect();
        Window window = this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        ActivityUtil activityUtil = (ActivityUtil) getApplication();
        activityUtil.setStatusbarHeight(rect.top);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        activityUtil.setDisplayWidth(displayMetrics.widthPixels);
        activityUtil.setDisplayHeight(displayMetrics.heightPixels);
    }

    private void setTitleBarPadding() {
        ActivityUtil activityUtil = (ActivityUtil) getApplication();
        View view = findViewById(R.id.pager);

        view.setPadding(0, activityUtil.getStatusbarHeight(), 0, 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //        outState.putString("played", String.valueOf(mPlayed));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //        String played = savedInstanceState.getString("played");
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onStartClick(ArrayList<Track> trackList, int position) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.includeMiniPlayer);
        if (fragment != null && fragment instanceof MiniPlayerFragment) {
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