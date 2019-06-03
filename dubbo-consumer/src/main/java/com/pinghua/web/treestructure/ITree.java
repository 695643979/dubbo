package com.pinghua.web.treestructure;

import java.util.List;

/**
 * @Date on 2019/4/17 20:44.
 * @Author: wenhuaping
 * @Version 1.0
 */
public interface ITree {
    public List<TreeNode> getTree();
    public List<TreeNode> getRoot();
    public TreeNode getTreeNode(String nodeId);
}
