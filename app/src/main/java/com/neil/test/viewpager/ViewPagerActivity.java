package com.neil.test.viewpager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.neil.test.R;
import com.neil.test.base.BaseBindingActivity;
import com.neil.test.databinding.ActivityViewpagerBinding;
import com.neil.test.viewpager.adapter.TabAdapter;
import com.neil.test.viewpager.adapter.TabFragmentAdapter;
import com.neil.test.viewpager.adapter.bean.Tab;
import com.neil.test.viewpager.fragment.TestOneFragment;
import com.neil.test.viewpager.fragment.TestThreeFragment;
import com.neil.test.viewpager.fragment.TestTwoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongnan1
 * @time 2021/7/22 20:35
 *
 */
public class ViewPagerActivity extends BaseBindingActivity<ActivityViewpagerBinding> implements TabAdapter.onTabClickListener {

    protected List<Tab> tabs;
    protected TabAdapter tabAdapter;
    protected TabFragmentAdapter tabFragmentAdapter;

    protected FragmentManager fragmentManager;

    @Override
    protected void initView() {
        super.initView();
        tabAdapter = new TabAdapter();
        fragmentManager = getSupportFragmentManager();
        tabs = new ArrayList<>();
        Tab tab1 = new Tab();
        tab1.setId(1);
        tab1.setName("tab 1");
        tab1.setTag(TestOneFragment.class.getSimpleName());
        tab1.setClazz(TestOneFragment.class);
        Tab tab2 = new Tab();
        tab2.setId(2);
        tab2.setName("tab 2");
        tab2.setTag(TestTwoFragment.class.getSimpleName());
        tab2.setClazz(TestTwoFragment.class);
        Tab tab3 = new Tab();
        tab3.setId(3);
        tab3.setName("tab 3");
        tab3.setTag(TestThreeFragment.class.getSimpleName());
        tab3.setClazz(TestThreeFragment.class);
        tabs.add(tab1);
        tabs.add(tab2);
        tabs.add(tab3);
        tabAdapter.setListener(this);
        tabAdapter.setTabs(tabs);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        mBinding.tabRecycler.setAdapter(tabAdapter);
        mBinding.tabRecycler.setLayoutManager(layoutManager);
        tabFragmentAdapter = new TabFragmentAdapter(fragmentManager, getLifecycle(), tabs);
        mBinding.viewpager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        mBinding.viewpager.setAdapter(tabFragmentAdapter);
        mBinding.viewpager.setCurrentItem(0, false);
        mBinding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabAdapter.setSelected(position);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_viewpager;
    }

    @Override
    public void onClick(int position, Tab tab) {
        mBinding.viewpager.setCurrentItem(position, false);
    }
}
