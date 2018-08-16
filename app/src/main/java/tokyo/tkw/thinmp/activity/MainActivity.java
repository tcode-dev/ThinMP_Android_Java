package tokyo.tkw.thinmp.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import tokyo.tkw.thinmp.favorite.FavoriteRegister;
import tokyo.tkw.thinmp.fragment.FavoriteListFragment;
import tokyo.tkw.thinmp.music.MusicList;
import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.ViewPagerAdapter;
import tokyo.tkw.thinmp.fragment.AlbumListFragment;
import tokyo.tkw.thinmp.fragment.ArtistListFragment;
import tokyo.tkw.thinmp.fragment.PlayerFragment;
import tokyo.tkw.thinmp.fragment.TrackListFragment;

public class MainActivity
        extends AppCompatActivity
        implements
        ArtistListFragment.OnFragmentInteractionListener,
        AlbumListFragment.OnFragmentInteractionListener,
        TrackListFragment.OnFragmentInteractionListener,
        PlayerFragment.OnFragmentInteractionListener,
        FavoriteListFragment.OnFragmentInteractionListener {

    private final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MusicList.setInstance(this);
        FavoriteRegister.setInstance(this);

        setPager();
    }

    /**
     * スワイプで画面を切り替える
     */
    private void setPager() {
        ViewPager viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
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

    /**
     * ユーザーにパーミッション権限を要求した承認結果を受け取る
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    rebuild();
                }

                return;
            }
        }

    }

    /**
     * 画面を再構築する
     */
    private void rebuild() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}