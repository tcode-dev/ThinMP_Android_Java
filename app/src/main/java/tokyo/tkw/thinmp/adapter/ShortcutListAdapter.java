package tokyo.tkw.thinmp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.shortcut.Shortcut;
import tokyo.tkw.thinmp.util.GlideUtil;
import tokyo.tkw.thinmp.viewHolder.ShortcutViewHolder;

public class ShortcutListAdapter extends RecyclerView.Adapter<ShortcutViewHolder> {
    private List<Shortcut> shortcutList;

    public ShortcutListAdapter(@NonNull List<Shortcut> shortcutList) {
        this.shortcutList = shortcutList;
    }

    @NonNull
    @Override
    public ShortcutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.panel2, parent,
                false);

        return new ShortcutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShortcutViewHolder holder, int position) {
        Shortcut shortcut = shortcutList.get(position);

        GlideUtil.bitmap(shortcut.albumArtId, holder.albumArt);
        holder.primaryText.setText(shortcut.name);
        holder.secondaryText.setText(shortcut.type);
    }

    @Override
    public int getItemCount() {
        return shortcutList.size();
    }
}
