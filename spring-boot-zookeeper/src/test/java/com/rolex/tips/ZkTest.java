package com.rolex.tips;

import com.google.gson.Gson;
import com.rolex.tips.lock.ZkLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class ZkTest {

    @Autowired
    CuratorFramework client;

    @Autowired
    ZkLock zkLock;

    @Test
    public void test01() throws Exception {
        Stat stat = client.checkExists().forPath("/zk/tips"); // 不存在返回null
        log.info("stat={}", stat);

        String node = client.create()
                .creatingParentsIfNeeded()  // 父节点不存在则创建
                .withMode(CreateMode.PERSISTENT)  //创建持久节点
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/zk/tips", "abc".getBytes());
        log.info("node={}", node);

        Stat stat1 = client.checkExists().forPath("/zk/tips");
        log.info("stat1={}", new Gson().toJson(stat1));
        log.info("version={}", stat1.getVersion());

        Stat stat2 = client.setData().forPath("/zk/tips", "123".getBytes());
        log.info("stat2={}", new Gson().toJson(stat2));
        log.info("version={}", stat2.getVersion());

        byte[] bytes = client.getData().forPath("/zk/tips");
        log.info("value={}", new String(bytes));

        Stat stat3 = new Stat();
        byte[] bytes1 = client.getData().storingStatIn(stat3).forPath("/zk/tips");
        log.info("version={}, value={}", stat3.getVersion(), new String(bytes1));

        Void result = client.delete()
                .guaranteed() //会话有效就一直删，直到成功删除
                .deletingChildrenIfNeeded() // 删除子节点
                //.withVersion(5)  // 版本不对会报异常
                .forPath("/zk");
        log.info("result={}", result);

        Stat stat4 = client.checkExists().forPath("/zk/tips");
        log.info("stat4={}", stat4);
    }

    @Test
    public void test02() throws Exception {
        //不监听当前节点变化，监听子节点变化
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/zk", true);
        pathChildrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        pathChildrenCache.getListenable().addListener((curatorFramework, event) -> {
            if (event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)) {
                log.info("watch --> 节点初始化");
            } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                log.info("watch --> 添加子节点, path={}, data={}", event.getData().getPath(), new String(event.getData().getData()));
            } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                log.info("watch --> 修改子节点, path={}, data={}", event.getData().getPath(), new String(event.getData().getData()));
            } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                log.info("watch --> 删除子节点, path={}", event.getData().getPath());
            }
        });

        Thread.sleep(1000);

        String node = client.create()
                .creatingParentsIfNeeded()
                .forPath("/zk/tom");
        log.info("node={}", node);

        Stat stat1 = client.setData().forPath("/zk/tom", "123".getBytes());
        log.info("stat1={}", new Gson().toJson(stat1));

        String node1 = client.create()
                .forPath("/zk/tom/jerry");
        log.info("node={}", node1);

        Void result = client.delete()
                .guaranteed() //会话有效就一直删，直到成功删除
                .deletingChildrenIfNeeded() // 删除子节点
                //.withVersion(5)  // 版本不对会报异常
                .forPath("/zk/tom");
        log.info("result={}", result);

    }

    @Test
    public void test03() throws Exception {
        //监听当前节点变化，不监听子节点变化
        String s = client.create().creatingParentsIfNeeded().forPath("/zk/jerry");
        log.info("init path: {}", s);
        Thread.sleep(3000);
        NodeCache nodeCache = new NodeCache(client, "/zk/jerry");
        nodeCache.start();
        nodeCache.getListenable().addListener(() -> {
            ChildData node = nodeCache.getCurrentData();
            if (node == null) {
                log.info("watch --> 节点删除");
            } else {
                String path = node.getPath();
                String data = new String(node.getData());
                log.info("watch --> 节点变更, path={}, data={}", path, data);
            }
        });
        Thread.sleep(1000);
        String node = client.create().creatingParentsIfNeeded().forPath("/zk/jerry/tom");
        log.info("node={}", node);
        Stat stat = client.setData().forPath("/zk/jerry/tom", "xyz".getBytes());
        log.info("stat={}", stat);
        byte[] bytes = client.getData().forPath("/zk/jerry/tom");
        log.info("path=/zk/jerry/tom, value={}", new String(bytes));
        Void unused = client.delete().deletingChildrenIfNeeded().forPath("/zk/jerry/tom");
        log.info("unused={}", unused);

        log.info("----------");
        Thread.sleep(3000);

        Stat stat1 = client.setData().forPath("/zk/jerry", "abc".getBytes());
        log.info("stat={}", stat1);
        byte[] bytes1 = client.getData().forPath("/zk/jerry");
        log.info("path=/zk/jerry, value={}", new String(bytes1));
        Void unused1 = client.delete().deletingChildrenIfNeeded().forPath("/zk/jerry");
        log.info("unused={}", unused1);
    }

    @Test
    public void test04() throws Exception {
        //监听当前节点及其所有子节点变化
        TreeCache treeCache = new TreeCache(client, "/zk");
        treeCache.start();
        treeCache.getListenable().addListener((curatorFramework, event) -> {
            String type = event.getType().name();
            log.info("watch --> type={}", type);
            Optional.ofNullable(event.getData()).ifPresent(t -> {
                String path = t.getPath();
                String data = new String(t.getData());
                log.info("watch --> type={}, path={}, data={}", type, path, data);
            });
        });

        log.info("创建子节点");
        String s = client.create().forPath("/zk/mouse", "jerry".getBytes());
        log.info("node={}", s);
        Thread.sleep(1000);

        log.info("修改子节点value");
        Stat stat = client.setData().forPath("/zk/mouse", "mickey".getBytes());
        Thread.sleep(1000);

        log.info("删除子节点");
        Void unused = client.delete().forPath("/zk/mouse");
        Thread.sleep(1000);

        log.info("修改当前节点value");
        Stat stat1 = client.setData().forPath("/zk", "abc".getBytes());
        Thread.sleep(1000);

        log.info("删除当前节点");
        Void unused1 = client.delete().forPath("/zk");

    }

    @Test
    public void test05() {
        String key = "zk_lock_key";
        new Thread(() -> {
            zkLock.lock(key);
            try {
                System.out.println(Thread.currentThread().getName() + " 获得锁开始处理");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                zkLock.unlock(key);
                System.out.println(Thread.currentThread().getName() + " 处理完成，释放锁");
            }
        }, "t1").start();
        new Thread(() -> {
            zkLock.lock(key);
            try {
                System.out.println(Thread.currentThread().getName() + " 获得锁开始处理");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                zkLock.unlock(key);
                System.out.println(Thread.currentThread().getName() + " 处理完成，释放锁");
            }
        }, "t2").start();
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test06() throws InterruptedException {
        String lockPath = "/zklock/mouse";

        new Thread(() -> {
            InterProcessMutex lock = new InterProcessMutex(client, lockPath);
            try {
                lock.acquire();
                System.out.println(Thread.currentThread().getName() + " 获得锁开始处理");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 处理完成，释放锁");
            }
        }, "jerry").start();
        new Thread(() -> {
            InterProcessMutex lock = new InterProcessMutex(client, lockPath);
            try {
                lock.acquire();
                System.out.println(Thread.currentThread().getName() + " 获得锁开始处理");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 处理完成，释放锁");
            }
        }, "mickey").start();

        Thread.sleep(15000);

    }
}
