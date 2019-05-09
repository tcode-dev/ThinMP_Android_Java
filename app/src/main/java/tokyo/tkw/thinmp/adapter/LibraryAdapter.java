package tokyo.tkw.thinmp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tokyo.tkw.thinmp.R;
import tokyo.tkw.thinmp.viewHolder.LibraryViewHolder;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryViewHolder> {
    private Activity mContext;
    private String[] mMenuLabelList;
    private Class<?>[] mMenuLinkList;
    private int mCount;

    public LibraryAdapter(@NonNull Activity context, @NonNull String[] menuLabelList,
                          Class<?>[] menuLinkList) {
        mContext = context;
        mMenuLabelList = menuLabelList;
        mMenuLinkList = menuLinkList;
        mCount = menuLabelList.length;
    }

    @Override
    public LibraryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_item,
                parent, false);

        return new LibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LibraryViewHolder holder, int position) {
        holder.libraryItem.setText(mMenuLabelList[position]);
        holder.itemView.setOnClickListener(onClickListener(mMenuLinkList[position]));
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    private View.OnClickListener onClickListener(Class<?> cls) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, cls);
                mContext.startActivity(intent);
            }
        };
    }
}
