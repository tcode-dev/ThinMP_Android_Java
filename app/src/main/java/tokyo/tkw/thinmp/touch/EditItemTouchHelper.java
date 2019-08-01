package tokyo.tkw.thinmp.touch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EditItemTouchHelper<T> {
    private RecyclerView.Adapter adapter;
    private List list;

    public EditItemTouchHelper(RecyclerView.Adapter adapter, List list) {
        this.adapter = adapter;
        this.list = list;
    }

    public ItemTouchHelper createItemTouchHelper() {
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT
        ) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();

                // viewの並び替え
                adapter.notifyItemMoved(fromPos, toPos);

                // dataの並び替え
                list.add(toPos, list.remove(fromPos));

                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int fromPos = viewHolder.getAdapterPosition();

                // 削除
                list.remove(fromPos);
                adapter.notifyItemRemoved(fromPos);
            }
        });
    }
}
