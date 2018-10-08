package tokyo.tkw.thinmp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import tokyo.tkw.thinmp.fragment.FavoriteListFragment;
import tokyo.tkw.thinmp.fragment.HorizontalFragment;
import tokyo.tkw.thinmp.fragment.PlaylistFragment;
import tokyo.tkw.thinmp.fragment.TopFragment;
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
            case 0: return new TopFragment();
            case 1: return new ArtistListFragment();
            case 2: return new AlbumListFragment();
            case 3: return new TrackListFragment();
            case 4: return new PlaylistFragment();
            case 5: return new FavoriteListFragment();
            case 6: return new HorizontalFragment();

            default: return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Top";
            case 1: return "アーティスト";
            case 2: return "アルバム";
            case 3: return "曲";
            case 4: return "プレイリスト";
            case 5: return "お気に入り";
            case 6: return "横スライド";

            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 7;
    }
}
