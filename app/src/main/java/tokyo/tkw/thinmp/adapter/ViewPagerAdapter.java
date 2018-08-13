package tokyo.tkw.thinmp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tokyo.tkw.thinmp.fragment.TrackListFragment;
import tokyo.tkw.thinmp.fragment.AlbumListFragment;
import tokyo.tkw.thinmp.fragment.ArtistListFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ArtistListFragment();
            case 1: return new AlbumListFragment();
            case 2: return new TrackListFragment();
            default: return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "アーティスト";
            case 1: return "アルバム";
            case 2: return "曲";
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
