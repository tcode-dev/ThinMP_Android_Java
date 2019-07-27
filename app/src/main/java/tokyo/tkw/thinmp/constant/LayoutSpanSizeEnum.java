package tokyo.tkw.thinmp.constant;

public enum LayoutSpanSizeEnum {
    LAYOUT(2),
    HEADER(2),
    LIST_ITEM_LINEAR(2),
    LIST_ITEM_GRID(1);

    private int spanSize;

    LayoutSpanSizeEnum(int spanSize) {
        this.spanSize = spanSize;
    }

    public int spanSize() {
        return spanSize;
    }
}
