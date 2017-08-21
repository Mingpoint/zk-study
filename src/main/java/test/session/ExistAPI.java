package test.session;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class ExistAPI implements Watcher{
	
	private static CountDownLatch count = new CountDownLatch(1);
	private static ZooKeeper zk = null;
	public static void main(String[] args) throws Exception {
		zk = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, new ExistAPI());
		String path = "/zk-book";
		count.await();
		zk.exists(path, true);
		zk.create(path, "222".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.setData(path, "44".getBytes(), -1);
		zk.create(path+"/test", "xx".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		zk.delete(path+"/test", -1);
		zk.delete(path, -1);
		Thread.sleep(Integer.MAX_VALUE);
	}
	@Override
	public void process(WatchedEvent event) {
		try {
			if(KeeperState.SyncConnected == event.getState()){
				if(EventType.None == event.getType() && null == event.getPath()){
					System.out.println("已经连接到zk："+event.getState());
					count.countDown();
				}else if(EventType.NodeCreated == event.getType()){
					System.out.println("Node "+event.getPath()+" create");
					zk.exists(event.getPath(), true);
				}else if(EventType.NodeDataChanged == event.getType()){
					System.out.println("Node "+event.getPath()+" change");
					zk.exists(event.getPath(), true);
				}else if(EventType.NodeDeleted == event.getType()){
					System.out.println("Node "+event.getPath()+" delete");
					zk.exists(event.getPath(), true);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
