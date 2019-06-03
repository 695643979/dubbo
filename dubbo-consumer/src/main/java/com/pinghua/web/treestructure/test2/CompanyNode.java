package com.pinghua.web.treestructure.test2;

import java.util.List;

/**
 * @Date on 2019/4/17 19:56.
 * @Author: wenhuaping
 * @Version 1.0
 * 用java代码将从数据库中取出的具有父子关系的数据转成json格式
 * 思路：
 * ①、取出数据中的所有父节点放入一个集合中
 * ②、取出数据中所有为该父节点的子节点放入另一个集合中
 * ③、用到迭代的方法将子节点一层一层的遍历
 * 工具类：
 */
public class CompanyNode {

    private String name ;//公司名称
    private String cybh;//成员编号
    private String sj_cybh;//上级成员编号
    private List<CompanyNode> child;//下级公司

    public CompanyNode() {
    }

    public CompanyNode(String name, String cybh, String sj_cybh,List<CompanyNode> child) {
        this.name = name;
        this.cybh = cybh;
        this.sj_cybh = sj_cybh;
        this.child=child;
    }

    public List<CompanyNode> getChild() {
        return child;
    }

    public void setChild(List<CompanyNode> child) {
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCybh() {
        return cybh;
    }

    public void setCybh(String cybh) {
        this.cybh = cybh;
    }

    public String getSj_cybh() {
        return sj_cybh;
    }

    public void setSj_cybh(String sj_cybh) {
        this.sj_cybh = sj_cybh;
    }

}
