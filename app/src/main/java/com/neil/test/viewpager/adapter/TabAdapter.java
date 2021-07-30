package com.neil.test.viewpager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.rxbinding2.view.RxView;
import com.neil.test.R;
import com.neil.test.databinding.ItemTabBinding;
import com.neil.test.viewpager.adapter.bean.Tab;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhongnan1
 * @time 2021/7/22 20:50
 *
 */
public class TabAdapter extends RecyclerView.Adapter<TabAdapter.TabViewHolder> {
    protected List<Tab> tabs = new ArrayList<>();
    protected onTabClickListener listener;
    @NonNull
    @Override
    public TabViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTabBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tab, parent, false);
        return new TabViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TabViewHolder holder, int position) {
        ItemTabBinding binding = holder.binding;
        Tab tab = tabs.get(position);
        binding.setTab(tab);

        RxView.clicks(binding.tabTitle)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe((v) -> {
                    setSelected(position);
                    if(listener != null) {
                        listener.onClick(position, tab);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return tabs == null ? 0 : tabs.size();
    }

    public List<Tab> getTabs() {
        return tabs;
    }

    public void setTabs(List<Tab> tabs) {
        this.tabs = tabs;
    }

    public onTabClickListener getListener() {
        return listener;
    }

    public void setListener(onTabClickListener listener) {
        this.listener = listener;
    }

    static class TabViewHolder extends RecyclerView.ViewHolder {
        ItemTabBinding binding;
        public TabViewHolder(@NonNull ItemTabBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setSelected(int position) {
        for(Tab tab : tabs) {
            tab.setSelected(false);
        }
        tabs.get(position).setSelected(true);
        notifyDataSetChanged();
    }
    public interface onTabClickListener {
        void onClick(int position, Tab tab);
    }
}
