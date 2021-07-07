package com.wolfsea.pulltorefreshdemo.loadrefreshlayout;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

public abstract class LoadRecyclerAdapter3 extends RecyclerView.Adapter {

    private View footView;
    private int footerCount;
    private int dataSize;
    private Handler handler = new Handler();

    void showFootView(boolean isShow) {
        if (isShow) {
            footerCount = 1;
        } else {
            footerCount = 0;
        }

        handler.post(this::notifyDataSetChanged);
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
        handler.post(this::notifyDataSetChanged);
    }

    void setFootView(View footView) {
        this.footView = footView;
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return onCreateItemViewHolder(parent);
        } else {
            return new MyFooterViewHolder(footView);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == 1 ? 1 : gridManager.getSpanCount();
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof MyFooterViewHolder)) {
            onBindItemViewHolder(holder, position);
        }
    }

    public abstract RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent);

    public abstract void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return dataSize + footerCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == dataSize) {
            return -1;
        } else {
            return 1;
        }
    }

    class MyFooterViewHolder extends RecyclerView.ViewHolder {
        MyFooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
