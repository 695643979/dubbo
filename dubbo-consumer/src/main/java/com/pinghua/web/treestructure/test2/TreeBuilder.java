package com.pinghua.web.treestructure.test2;


import java.util.ArrayList;
import java.util.List;

/**
 * @Date on 2019/4/17 19:55.
 * @Author: wenhuaping
 * @Version 1.0
 */
public class TreeBuilder {

    List<CompanyNode> nodes = new ArrayList<CompanyNode>();

    public TreeBuilder(List<CompanyNode> nodes) {
        super();
        this.nodes= nodes;
    }

    /**
     * 构建JSON树形结构
     * @return
     */
    public String buildJSONTree() {
        List<CompanyNode> nodeTree = buildTree();
//        JSONArray jsonArray = JSONArray.fromObject(nodeTree);
//        return jsonArray.toString();
        return null;
    }


    /**
     * 构建树形结构
     * @return
     */
    public List<CompanyNode> buildTree() {
        List<CompanyNode> treeNodes = new ArrayList<CompanyNode>();
        List<CompanyNode> rootNodes = getRootNodes();
        for (CompanyNode rootNode : rootNodes) {
            buildChildNodes(rootNode);
            treeNodes.add(rootNode);
        }
        return treeNodes;
    }

    /**
     * 递归子节点
     * @param node
     */
    public void buildChildNodes(CompanyNode node) {
        List<CompanyNode> children = getChildNodes(node);
        if (!children.isEmpty()) {
            for(CompanyNode child : children) {
                buildChildNodes(child);
            }
            node.setChild(children);
        }
    }

    /**
     * 获取父节点下所有的子节点
     * @param pnode
     * @return
     */
    public List<CompanyNode> getChildNodes(CompanyNode pnode) {//传入父节点对象，如果为该父节点的子节点，则放入子节点集合中
        List<CompanyNode> childNodes = new ArrayList<CompanyNode>();
        for (CompanyNode n : nodes){//从nodes中筛选所以为pnode的子节点
            if (pnode.getCybh().equals(n.getSj_cybh())) {
                childNodes.add(n);
            }
        }
        return childNodes;
    }

    /**
     * 判断是否为根节点
     * @param node
     * @return
     */
    public boolean rootNode(CompanyNode node) {
        boolean isRootNode = true;
        for (CompanyNode n : nodes){//从nodes中筛选所以父节点
            if (node.getSj_cybh().equals(n.getCybh())) {//判断传入的node对象中，他的上级成员编号还有没有node中的成员编号与之对应，如果没有，则为根节点
                isRootNode= false;
                break;
            }
        }
        return isRootNode;
    }

    /**
     * 获取集合中所有的根节点
     * @return
     */
    public List<CompanyNode> getRootNodes() {
        List<CompanyNode> rootNodes = new ArrayList<CompanyNode>();
        for (CompanyNode n : nodes){
            if (rootNode(n)) {
                rootNodes.add(n);//把所以的根节点放入rootNodes集合中
            }
        }
        return rootNodes;
    }

}
