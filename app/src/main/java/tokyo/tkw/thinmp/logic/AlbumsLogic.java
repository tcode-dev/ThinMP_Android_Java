package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.adapter.AlbumListAdapter;
import tokyo.tkw.thinmp.dto.AlbumsDto;
import tokyo.tkw.thinmp.music.AlbumCollection;

public class AlbumsLogic {
    private Context context;
    private AlbumCollection albumCollection;

    private AlbumsLogic(Context context) {
        this.context = context;
        this.albumCollection =  AlbumCollection.createInstance(context);
    }

    public static AlbumsLogic createInstance(Context context) {
        return new AlbumsLogic(context);
    }

    public AlbumsDto createDto() {
        AlbumsDto dto = new AlbumsDto();

        dto.title =context.getResources().getString(R.string.albums);
        dto.albumList = albumCollection.findAll();
        dto.titleSpanSize = 2;
        dto.albumListSpanSize = 1;

        return dto;
    }
}
