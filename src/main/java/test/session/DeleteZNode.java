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
import org.apache.zookeeper.data.Stat;

public class DeleteZNode implements Watcher{
private static CountDownLatch count = new CountDownLatch(1);
	
	public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
		ZooKeeper zoo = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, new DeleteZNode());
		count.await();
		String path = zoo.create("/del", "124rf".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
		List<String> children = zoo.getChildren("/", null);
		for(String tr : children){
			System.out.println("tr===="+tr);
		}
		Stat exists = zoo.exists("/zk-0p-psss", false);
		
		if(exists!= null && exists.getVersion() >= 0){
			zoo.delete("/zk-0p-psss", -1);
		}else{
			System.out.println("zNode not exits!");
		}
		List<String> children1 = zoo.getChildren("/", null);
		for(String tr1 : children1){
			System.out.println("tr1===="+tr1);
		}
		Thread.sleep(Integer.MAX_VALUE);
	}

	@Override
	public void process(WatchedEvent e) {
		if(KeeperState.SyncConnected == e.getState()){
			count.countDown();
		}
	}
}
