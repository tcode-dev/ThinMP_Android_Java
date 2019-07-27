package tokyo.tkw.thinmp.logic;

import android.content.Context;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.constant.LayoutSpanSizeEnum;
import tokyo.tkw.thinmp.dto.AlbumsDto;
import tokyo.tkw.thinmp.album.Albums;

public class AlbumsLogic {
    private Context context;
    private Albums albums;

    private AlbumsLogic(Context context) {
        this.context = context;
        this.albums = Albums.createInstance(context);
    }

    public static AlbumsLogic createInstance(Context context) {
        return new AlbumsLogic(context);
    }

    public AlbumsDto createDto() {
        AlbumsDto dto = new AlbumsDto();

        dto.title = context.getResources().getString(R.string.albums);
        dto.albumList = albums.getAlbumList();
        dto.layoutSpanSize = LayoutSpanSizeEnum.LAYOUT.spanSize();
        dto.headerSpanSize = LayoutSpanSizeEnum.HEADER.spanSize();
        dto.listSpanSize = LayoutSpanSizeEnum.LIST_ITEM_GRID.spanSize();

        return dto;
    }
}
