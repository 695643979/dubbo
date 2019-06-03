package com.pinghua.web.treestructure;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import java.util.ArrayList;
import java.util.List;


/**
 * @Date on 2019/4/17 20:48.
 * @Author: wenhuaping
 * @Version 1.0
 */
public class TreeDemo {

    public static void main(String[] args) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(); // 构造方法里，也可以直接传需要序列化的属性名字
        filter.getExcludes().add("parent");
        filter.getExcludes().add("allChildren");
//        List<ITreeNode> list = genOrgList();
        Tree tree = new Tree(genOrgList());
        //堆栈溢出
//        System.out.println("tree: " +  JSON.toJSONString(tree, SerializerFeature.DisableCircularReferenceDetect));
//        System.out.println("tree2: " +  JSON.toJSONString(tree, filter));


//        System.out.println("根节点： " +  JSONObject.toJSONString(tree.getRoot()));
//        System.out.println("数据1：" + JSONObject.toJSONString(tree.getTree(), filter));


        TreeNode treeNode = tree.getTreeNode("1");
//        System.out.println("数据2： " + JSONObject.toJSONString(treeNode));

        List<TreeNode> list = treeNode.getChildren();
        String data = JSONObject.toJSONString(list, filter);
        System.out.println("数据3： " + data);
    }

    public static List<ITreeNode> genOrgList(){
        List<ITreeNode> list = new ArrayList<ITreeNode>();

        //这些数据对应数据库中的每一笔记录，数据库表设计设计成父子节点
        Org org = new Org("1", "0", "北京市", 2, "110000", "2");
        list.add(org);
        org = new Org("3", "1", "市辖区", 3, "110100", "3");
        list.add(org);
        org = new Org("4", "3", "东城区", 4, "110101", "4");
        list.add(org);
        org = new Org("5", "3", "东城区", 5, "110102", "4");
        list.add(org);
        org = new Org("6", "3", "东城区", 6, "110105", "4");
        list.add(org);
        org = new Org("7", "3", "东城区", 7, "110106", "4");
        list.add(org);
        org = new Org("8", "3", "东城区", 8, "110107", "4");
        list.add(org);
        org = new Org("9", "3", "东城区", 9, "110108", "4");
        list.add(org);
        org = new Org("10", "3", "东城区", 10, "110109", "4");
        list.add(org);
        org = new Org("11", "3", "东城区", 11, "110111", "4");
        list.add(org);
        org = new Org("12", "3", "东城区", 12, "110112", "4");
        list.add(org);
        org = new Org("13", "3", "东城区", 13, "110113", "4");
        list.add(org);
        org = new Org("14", "3", "东城区", 14, "110114", "4");
        list.add(org);
        org = new Org("15", "3", "东城区", 15, "110115", "4");
        list.add(org);
        org = new Org("16", "3", "东城区", 16, "110116", "4");
        list.add(org);
        org = new Org("17", "3", "东城区", 17, "110117", "4");
        list.add(org);
        org = new Org("18", "1", "县", 3, "110200", "3");
        list.add(org);
        org = new Org("19", "18", "密云县", 19, "110228", "4");
        list.add(org);
        org = new Org("20", "18", "延庆县", 20, "110229", "4");
        list.add(org);
        org = new Org("21", "20", "万能县", 21, "110230", "4");
        list.add(org);
        return list;
    }


}
