package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.dto.MainDto;
import tokyo.tkw.thinmp.provider.AlbumContentProvider;

public class MainLogic {
    private Context context;
    private AlbumContentProvider albumContentProvider;

    private MainLogic(Context context) {
        this.context = context;
        this.albumContentProvider = new AlbumContentProvider(context);
    }

    public static MainLogic createInstance(Context context) {
        return new MainLogic(context);
    }

    public MainDto createDto() {
        MainDto dto = new MainDto();

        dto.menuLabelList = context.getResources().getStringArray(R.array.library_menu);
        dto.albumList = albumContentProvider.findAll();

        return dto;
    }
}
