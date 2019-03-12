package com.pinghua.web.demo3;

import org.apache.zookeeper.KeeperException;
import java.io.IOException;


/**
 * zookeeper实现分布式锁，注意代码只做演示用，并不适合生产环境使用！！！
 *
 * 分布式锁有多种实现方式，比如通过数据库、redis都可实现。作为分布式协同工具ZooKeeper，当然也有着标准的实现方式。本文介绍在zookeeper中如何实现排他锁。
 *
 * 1.0版本
 * 首先我们先介绍一个简单的zookeeper实现分布式锁的思路：
 *
 * 用zookeeper中一个临时节点代表锁，比如在/exlusive_lock下创建临时子节点/exlusive_lock/lock。
 * 所有客户端争相创建此节点，但只有一个客户端创建成功。
 * 创建成功代表获取锁成功，此客户端执行业务逻辑
 * 未创建成功的客户端，监听/exlusive_lock变更
 * 获取锁的客户端执行完成后，删除/exlusive_lock/lock，表示锁被释放
 * 锁被释放后，其他监听/exlusive_lock变更的客户端得到通知，再次争相创建临时子节点/exlusive_lock/lock。此时相当于回到了第2步。
 * 我们的程序按照上述逻辑直至抢占到锁，执行完业务逻辑。
 *
 * 上述是较为简单的分布式锁实现方式。能够应付一般使用场景，但存在着如下两个问题：
 * 1、锁的获取顺序和最初客户端争抢顺序不一致，这不是一个公平锁。每次锁获取都是当次最先抢到锁的客户端。
 * 2、羊群效应，所有没有抢到锁的客户端都会监听/exlusive_lock变更。当并发客户端很多的情况下，所有的客户端都会接到通知去争抢锁，此时就出现了羊群效应。
 *
 * 为了解决上面的问题，我们重新设计。
 *
 * 2.0版本
 * 我们在2.0版本中，让每个客户端在/exlusive_lock下创建的临时节点为有序节点，这样每个客户端都在/exlusive_lock下有自己对应的锁节点，而序号排在最前面的节点，代表对应的客户端获取锁成功。排在后面的客户端监听自己前面一个节点，那么在他前序客户端执行完成后，他将得到通知，获得锁成功。逻辑修改如下：
 *
 * 每个客户端往/exlusive_lock下创建有序临时节点/exlusive_lock/lock_。创建成功后/exlusive_lock下面会有每个客户端对应的节点，如/exlusive_lock/lock_000000001
 * 客户端取得/exlusive_lock下子节点，并进行排序，判断排在最前面的是否为自己。
 * 如果自己的锁节点在第一位，代表获取锁成功，此客户端执行业务逻辑
 * 如果自己的锁节点不在第一位，则监听自己前一位的锁节点。例如，自己锁节点lock_000000002，那么则监听lock_000000001.
 * 当前一位锁节点（lock_000000001）对应的客户端执行完成，释放了锁，将会触发监听客户端（lock_000000002）的逻辑。
 * 监听客户端重新执行第2步逻辑，判断自己是否获得了锁。
 * 如此修改后，每个客户端只关心自己前序锁是否释放，所以每次只会有一个客户端得到通知。而且，所有客户端的执行顺序和最初锁创建的顺序是一致的。解决了1.0版本的两个问题。
 *
 *
 * Created by wenhuaping on 2019/3/12.
 * @author: wenhuaping
 */
public class Test {

    /**
     * main函数中我们循环调用ticketSeller.sellTicketWithLock()，执行加锁后的卖票逻辑。
     * @param args
     * @throws KeeperException
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main (String[] args) throws KeeperException, InterruptedException, IOException {
        TicketSeller ticketSeller = new TicketSeller();
        for(int i=0;i<1000;i++){
            ticketSeller.sellTicketWithLock();
        }
    }

}
