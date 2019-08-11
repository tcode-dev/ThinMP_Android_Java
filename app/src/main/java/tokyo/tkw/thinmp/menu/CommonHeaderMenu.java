package tokyo.tkw.thinmp.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.PopupMenu;

import tokyo.tkw.thinmp.R;

public class CommonHeaderMenu {
    private Context context;
    private View view;
    private Class<?> link;

    public CommonHeaderMenu(View view, Class<?> link) {
        this.context = view.getContext();
        this.view = view;
        this.link = link;
    }

    /**
     * メニューを生成して表示する
     */
    @SuppressLint("ResourceType")
    public void show() {
        PopupMenu popupMenu = new PopupMenu(context, view);
        popupMenu.getMenuInflater().inflate(R.layout.popup_menu_common_header, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(createMenuItemClickListener());
        popupMenu.show();
    }

    /**
     * メニューのイベント
     */
    private PopupMenu.OnMenuItemClickListener createMenuItemClickListener() {
        return item -> {
            if (item.getItemId() == R.id.edit) {
                edit();

                return true;
            }

            return false;
        };
    }

    private void edit() {
        Context context = view.getContext();
        Intent intent = new Intent(context, link);
        context.startActivity(intent);
    }
}
