package test.session;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class TestCreateSimpleSession implements Watcher{
	private static CountDownLatch CountDownLatch = new CountDownLatch(1);
	public static void main(String[] args) throws IOException {
		//这里我用的是虚拟机装的伪集群，故host是一样的
		ZooKeeper zooKeeper = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, new TestCreateSimpleSession());
		System.out.println("======="+zooKeeper.getState());
		try {
			CountDownLatch.await();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("session opened!");
	}

	@Override
	public void process(WatchedEvent watchedevent) {
		System.out.println("path:"+watchedevent.getPath()+"		state"+watchedevent.getState()+"	type"+watchedevent.getType());
		if(KeeperState.SyncConnected ==  watchedevent.getState()){
			CountDownLatch.countDown();
		}
	}

}
