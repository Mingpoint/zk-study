package test.session;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class FindZNodeList implements Watcher{
	private static CountDownLatch count = new CountDownLatch(1);
	private static ZooKeeper zoo = null;
	
	public static void main(String[] args) {
		String path = "/zk-0p-psss";
		try {
			
			zoo = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, new FindZNodeList());
			count.await();
			zoo.create(path, "passs".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			zoo.create(path+"/c1", "pass".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			List<String> children = zoo.getChildren(path, true);
			System.out.println(children);
			
			zoo.create(path+"/c2", "pas".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			Thread.sleep(Integer.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void process(WatchedEvent e) {
		System.out.println(e.getState());
		if(e.getState() == KeeperState.SyncConnected){
			if(EventType.None == e.getType() && null == e.getPath()){
				count.countDown();
			}else if(EventType.NodeChildrenChanged == e.getType()){
				try {
					System.out.println(zoo.getChildren(e.getPath(), true));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
}
