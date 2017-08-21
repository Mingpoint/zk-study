package test.session;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class TestSessionBySessionId implements Watcher{
	private static CountDownLatch count = new CountDownLatch(1);
	public static void main(String[] args) throws IOException {
		ZooKeeper zoo = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, new TestSessionBySessionId());
		System.out.println(zoo.getState());
		try {
			count.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		byte[] sessionPasswd = zoo.getSessionPasswd();
		long sessionId = zoo.getSessionId();
		System.out.println("sessionId="+sessionId+"	sessionPasswd="+sessionPasswd);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ZooKeeper zoo1 = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, new TestSessionBySessionId(), sessionId, sessionPasswd);
		System.out.println("zoo1.getSessionId()="+zoo1.getSessionId()+"	zoo1.getSessionPasswd()="+zoo1.getSessionPasswd());
	}
	@Override
	public void process(WatchedEvent event) {
		System.out.println("	state:"+event.getState()+"	");
		if(KeeperState.SyncConnected == event.getState()){
			count.countDown();
		}
	}

}
