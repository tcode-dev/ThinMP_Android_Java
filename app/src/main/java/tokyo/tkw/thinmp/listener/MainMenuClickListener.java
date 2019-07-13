package tokyo.tkw.thinmp.listener;

import android.content.Context;
import android.content.Intent;
import android.view.View;

public class MainMenuClickListener implements View.OnClickListener {
    private Class<?> link;

    public MainMenuClickListener(Class<?> link) {
        this.link = link;
    }

    @Override
    public void onClick(View view) {
        Context context = view.getContext();
        Intent intent = new Intent(context, link);
        context.startActivity(intent);
    }
}
