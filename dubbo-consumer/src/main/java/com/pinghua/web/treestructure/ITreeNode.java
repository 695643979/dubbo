package com.pinghua.web.treestructure;

/**
 * @Date on 2019/4/17 20:46.
 * @Author: wenhuaping
 * @Version 1.0
 */
public interface ITreeNode {
    public String getNodeId();
    public String getNodeName();
    public String getNodeParentId();
    public Integer getOrderNum();
}
