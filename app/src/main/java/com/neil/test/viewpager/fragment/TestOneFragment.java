package com.neil.test.viewpager.fragment;

import android.view.View;

import com.neil.test.R;
import com.neil.test.base.AndroidXLazyBindingFragment;
import com.neil.test.databinding.FragmentTestOneBinding;
import com.neil.test.widget.nodeprogressbar.NodeProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongnan1
 * @time 2021/7/22 21:11
 *
 */
public class TestOneFragment extends AndroidXLazyBindingFragment<FragmentTestOneBinding> {

    @Override
    public void initView(View view) {
        super.initView(view);
        initNodeProgressBar1();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_test_one;
    }


    private void initNodeProgressBar1() {
        List<NodeProgressBar.Node> nodeList = new ArrayList<>();

        NodeProgressBar.Node node1 = new NodeProgressBar.Node();
        node1.nodeState = NodeProgressBar.Node.NodeState.SUCCEED;
        node1.nodeAfterLineState = NodeProgressBar.Node.LineState.REACHED;
        node1.id = 1;
        node1.topTxt = "青铜";
        node1.centerTxt = "1";
        node1.bottomTxt = "入门";
        nodeList.add(node1);

        NodeProgressBar.Node node2 = new NodeProgressBar.Node();
        node2.nodeState = NodeProgressBar.Node.NodeState.SUCCEED;
        node2.nodeAfterLineState = NodeProgressBar.Node.LineState.REACHED;
        node2.id = 2;
        node2.topTxt = "白银";
        node2.centerTxt = "2";
        node2.bottomTxt = "初级";
        nodeList.add(node2);

        NodeProgressBar.Node node3 = new NodeProgressBar.Node();
        node3.nodeState = NodeProgressBar.Node.NodeState.REACHED;
        node3.nodeAfterLineState = NodeProgressBar.Node.LineState.REACHED;
        node3.id = 3;
        node3.topTxt = "黄金";
        node3.centerTxt = "3";
        node3.bottomTxt = "中级";
        nodeList.add(node3);

        NodeProgressBar.Node node4 = new NodeProgressBar.Node();
        node4.nodeState = NodeProgressBar.Node.NodeState.UNREACHED;
        node4.nodeAfterLineState = NodeProgressBar.Node.LineState.UNREACHED;
        node4.id = 4;
        node4.topTxt = "钻石";
        node4.centerTxt = "4";
        node4.bottomTxt = "高级";
        nodeList.add(node4);

        NodeProgressBar.Node node5 = new NodeProgressBar.Node();
        node5.nodeState = NodeProgressBar.Node.NodeState.UNREACHED;
        node5.nodeAfterLineState = NodeProgressBar.Node.LineState.UNREACHED;
        node5.id = 5;
        node5.topTxt = "星耀";
        node5.centerTxt = "5";
        node5.bottomTxt = "专家";
        nodeList.add(node5);

        mBinding.progressBar.setNodeList(nodeList);
    }
}
