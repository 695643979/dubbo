package com.pinghua.web.zookeeper.demo1;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 功能：使用zookeeper实现分布式锁
 * Created by wenhuaping on 2019/3/12.
 * @author: wenhuaping
 *
 * 如何使用zookeeper实现分布式锁？
 *
 * 在描述算法流程之前，先看下zookeeper中几个关于节点的有趣的性质：
 * 1.有序节点：假如当前有一个父节点为/lock，我们可以在这个父节点下面创建子节点；
 *  zookeeper提供了一个可选的有序特性，例如我们创建的子节点 "/lock/node-" 并且指明有序，那么
 *  zookeeper在生产子节点时会跟进当前的子节点数量自动添加整数序号，也就是说如果是第一个创建的
 *  子节点，那么生成的子节点为 /lock/node-0000000000 , 下一个节点则为 /lock/node-0000000001,依次
 *  类推；
 * 2.临时节点：客户端可以缉拿了一个临时节点，在会话结束或者会话超时后，zookeeper会自动删除该节点。
 * 3.事件监听：在读取数据时，我们可以同时对节点设置事件监听，当节点数据或者结构变化时，zookeeper
 * 会通知客户端。当前zookeeper有如下四种事件： 1) 节点创建；2）节点删除；3）节点数据修改；4）子节点变更；
 *
 *
 * 下面描述使用zookeeper实现分布式锁的算法流程，假设锁空间的根节点为/lock：
 * 1.客户端连接zookeeper，并在/lock下创建临时的且有序的子节点，第一个客户端对应的子节点为/lock/lock-0000000000 ,
 * 第二个为/lock/node-0000000001，以此类推。
 * 2.客户端获取/lock下的子节点，判断自己创建的子节点是否为当前子节点列表中序号最小的子节点，如果是，则认为获得锁，
 * 否则监听/lock的子节点变更消息，获得子节点变更通知后重复此步骤直至获得锁；（改进：否则监听刚好在自己之前一位的子节点
 * 删除消息，或者子节点变更通知后重复此步骤直至获得锁；）
 * 3.执行业务代码；
 * 4.完成业务流程后，删除对应的子节点释放锁；
 *
 * 注意：
 * 步骤1中创建的临时节点能够保证在故障的情况下锁也能被释放，考虑这么个场景：假如客户端a当前创建的子节点为序号最小的节点，
 * 获得锁之后客户端所在机器宕机了，客户端没有主动删除子节点；如果创建的是永久的节点，那么这个锁永远不会释放，导致死锁；由
 * 于创建的是临时节点，客户端宕机后，过了一定时间zookeeper没有收到客户端的心跳包判断会话失效，将临时节点删除从而释放锁。
 *
 * 另外细心的朋友可能会想到，在步骤2中获取子节点列表与设置监听这两步操作的原子性问题，考虑这么个场景：客户端a对应子节点为
 * /lock/lock-0000000000，客户端b对应子节点为/lock/lock-0000000001，客户端b获取子节点列表时发现自己不是序号最小的，但是在
 * 设置监听器前客户端a完成业务流程删除了子节点/lock/lock-0000000000，客户端b设置的监听器岂不是丢失了这个事件从而导致永远等
 * 待了？这个问题不存在的。因为zookeeper提供的API中设置监听器的操作与读操作是原子执行的，也就是说在读子节点列表时同时设置
 * 监听器，保证不会丢失事件。
 *
 * 最后，对于这个算法有个极大的优化点：假如当前有1000个节点在等待锁，如果获得锁的客户端释放锁时，这1000个客户端都会被唤醒，
 * 这种情况称为“羊群效应”；在这种羊群效应中，zookeeper需要通知1000个客户端，这会阻塞其他的操作，最好的情况应该只唤醒新的
 * 最小节点对应的客户端。应该怎么做呢？在设置事件监听时，每个客户端应该对刚好在它之前的子节点设置事件监听，例如子节点列表为
 * /lock/lock-0000000000、/lock/lock-0000000001、/lock/lock-0000000002，序号为1的客户端监听序号为0的子节点删除消息，序号为2
 * 的监听序号为1的子节点删除消息。
 *
 */
public class ZookeeperDistributedLocks {

    public static void main(String[] args) {
        try {
            // 创建zookeeper的客户端
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
            CuratorFramework client = CuratorFrameworkFactory.newClient("47.106.95.116:2181", 100, 100, retryPolicy);
            client.start();

            //创建分布式锁，锁空间的根节点路径 /curator/lock
            //这里有个地方需要注意，当与zookeeper通信存在异常时，acquire会直接抛出异常，需要使用者自身做重试策略。代码中调用了internalLock(-1, null)，参数表明在锁被占用时永久阻塞等待。internalLock的代码如下：
            InterProcessMutex mutex = new InterProcessMutex(client, "/curator/lock");
            mutex.acquire();

            //获得了锁，进行业务流程
            System.out.println("Enter mutex!");

            //完成业务流程，释放锁
            mutex.release();

            //关闭客户端
            client.close();

        } catch (Exception e) {

        }



    }
}
