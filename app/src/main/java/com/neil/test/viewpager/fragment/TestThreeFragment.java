package com.neil.test.viewpager.fragment;

import com.neil.test.R;
import com.neil.test.base.AndroidXLazyBindingFragment;
import com.neil.test.databinding.FragmentTestThreeBinding;
import com.neil.test.databinding.FragmentTestTwoBinding;
/**
 * @author zhongnan1
 * @time 2021/7/22 21:11
 *
 */
public class TestThreeFragment extends AndroidXLazyBindingFragment<FragmentTestThreeBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_test_three;
    }
}
