package com.neil.test.viewpager.adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.neil.test.viewpager.adapter.bean.Tab;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongnan1
 * @time 2021/7/23 9:36
 *
 */
public class TabFragmentAdapter extends FragmentStateAdapter {

    protected final static String TAG = "TabFragmentAdapter";

    List<Tab> tabs;
    FragmentManager fragmentManager;


    public TabFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, @NonNull List<Tab> tabs) {
        super(fragmentManager, lifecycle);
        this.fragmentManager = fragmentManager;
        this.tabs = tabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String tag = tabs.get(position).getTag();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if(fragment != null) {
            return fragment;
        } else {
            try {
                fragment = (Fragment) tabs.get(position).getClazz().newInstance();
                return fragment;
            } catch (Exception e) {
                Log.d(TAG, "createFragment: " + e);
                return new Fragment();
            }
        }
    }

    @Override
    public int getItemCount() {
        return tabs == null ? 0 : tabs.size();
    }
}
