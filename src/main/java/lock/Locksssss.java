package lock;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Locksssss implements Watcher{
	private final static int LENGTH = 10;
	//zk连接
	private static CountDownLatch count = new CountDownLatch(1);
	//线程
	private static CountDownLatch countsss = new CountDownLatch(LENGTH);
	private final static String threadGroup = "zk/threadTest"; 
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < LENGTH; i++) {
			System.out.println("[第]"+i+1+"开始");
			ZooKeeper zk = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, new Locksssss());
			count.await();
			ZkConnetionss con = new ZkConnetionss();
		}
	}
	@Override
	public void process(WatchedEvent watchedevent) {
		
	}
}
