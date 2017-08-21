package test.session;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

/**
 * 同步
 * @author Mingpoint
 * @date 2017年7月14日 下午1:49:14 
 *
 */
public class CreateZNodeBySync implements Watcher{
	private static CountDownLatch count = new CountDownLatch(1);
	public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
		ZooKeeper zooKeeper = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, new CreateZNodeBySync());
		count.await();
		String path1 = zooKeeper.create("/zk-test4-sync", "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		System.out.println("path1="+path1);
		String path2 = zooKeeper.create("/zk-test3-sync", "456".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		System.out.println("path2="+path2);
		List<String> children = zooKeeper.getChildren("/", null);
		for (String str : children) {
			System.out.println("str="+str);
		}
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println(event.getState());
		if(event.getState() == KeeperState.SyncConnected){
			count.countDown();
		}
		
	}

}
