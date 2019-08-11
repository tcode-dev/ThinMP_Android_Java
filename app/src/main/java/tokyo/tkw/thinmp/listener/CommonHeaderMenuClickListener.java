package tokyo.tkw.thinmp.listener;

import android.view.View;

import tokyo.tkw.thinmp.menu.CommonHeaderMenu;

public class CommonHeaderMenuClickListener implements View.OnClickListener {
    private Class<?> link;

    public CommonHeaderMenuClickListener(Class<?> link) {
        this.link = link;
    }

    @Override
    public void onClick(View view) {
        CommonHeaderMenu menu = new CommonHeaderMenu(view, link);
        menu.show();
    }
}