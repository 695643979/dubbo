package com.pinghua.web.demo2;

import com.pinghua.vo.Product;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by wenhuaping on 2019/3/12.
 * @author: wenhuaping
 * 1. Zookeeper简述
 *      从官网的介绍中，可以总结出，Zookeeper是一个集中式服务，它能够实现高度可靠的分布式协调，可用于开发和维护开源服务器。
 *  除了官网的解释外，我的观点是还可以这样理解。它也相当于是一个数据库，具有数据同步和选举功能，能够用来存储一些信息，可用于
 *  解决大数据集群的单点故障问题。Zookeeper有leader和follow两种角色，当leader的节点宕掉之后，会自动选举出新的leader，如果只剩
 *  一个节点活着，就是standalone状态。Zookeeper各个节点之间的数据会自动同步，比如在Zookeeper集群的A节点存储数据，那么这份数据
 *  也会自动拷贝到集群中另外的节点上。在Hadoop、Storm、Spark集群都可以使用Zookeeper实现高可用（HA），防止出现单点故障。
 *
 * 2. 为什么要加锁
 *     在多线程编程中，必须要考虑到线程安全问题，当共享数据被高并发访问时，会破坏数据的一致性。比如抢购商品，商品数量为1，有两个用户
 * （线程）同时对它进行访问，当第一个线程拿到数据，还没有对数量执行减1操作的这段时间，第二个线程在这个时间段也拿到了数据，两个线程都对
 * 商品数量进行减1操作的话，就会出现商品数量是 -1 的数据，就违背了实际原则。
 *      因此，在程序中引入了锁，在线程访问共享数据之前，首先要请求锁，当得到这把锁的时候，才能够访问共享数据，使用完以后再归还这把锁。
 * 如果锁已经被一个线程获取，其它线程就请求不到锁，就执行重试策略，进入等待状态，不会访问共享数据，也就保证了数据的一致性。
 *
 */
public class ZookeeperDistributedLocksClient {


    /**
     * 抢购商品的方法
     * 作用：访问共享资源,获取并更新商品数量
     */
    public static void buy() {
        System.out.println("--------【"+Thread.currentThread().getName()+"】开始购买-------");

        //这里可以优化一下，使用redis缓存
        //获取商品数量
        int currentNumber = Product.getNumber();

        /*
         * 如果商品数量为0,则不能购买
         * 如果还有商品,则执行购买操作
         */
        if (currentNumber == 0) {
            System.out.println("商品已被抢空！！！");
        } else {
            System.out.println("当前商品数量："+currentNumber);

            //购买后商品数量减1
            currentNumber--;
            Product.setNumber(currentNumber);

            //为了便于观察程序的运行结果，这里使线程在执行购买操作后，停顿0.1秒
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("--------【"+Thread.currentThread().getName()+"】  购买结束-------");
    }

    public static void main (String[] args) {

        /**
         * 定义重试策略：等待2秒,重试10次
         * 第一个参数：等待时间
         * 第二个参数：重试次数
         */
        RetryPolicy policy = new ExponentialBackoffRetry(2000, 10);

        /**
         * 创建客户端向zookeeper请求锁
         * connectString() : zookeeper地址
         * retryPolicy() : 重试策略
         */
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder().connectString("47.106.95.116:2181").retryPolicy(policy).build();
        //启用
        curatorFramework.start();

        //获取zookeeper锁的信息
        final InterProcessMutex mutex = new InterProcessMutex(curatorFramework, "/curator/lock");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * 创建8个线程模拟8个客户端并发访问
         *
         */
        for (int i = 0; i < 20; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        //请求锁资源，如果没有得到锁资源，就会执行重试策略
                        mutex.acquire();
                        //开始访问共享资源，这里是访问商品信息
                        buy();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            //将锁归还
                            mutex.release();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

    }

}
