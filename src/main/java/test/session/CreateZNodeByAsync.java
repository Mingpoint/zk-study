package test.session;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 异步创建
 * @author Mingpoint
 * @date 2017年7月14日 下午2:37:18 
 *
 */
public class CreateZNodeByAsync implements Watcher{
	private static CountDownLatch count = new CountDownLatch(1);
	
	public static void main(String[] args) throws IOException, InterruptedException {
		ZooKeeper zoo = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, new CreateZNodeByAsync());

		count.await();
		zoo.create("/zk-test5-asyanc-", "eee".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, new CallBackImpl(), "I am back 1!");
		zoo.create("/zk-test6-asyanc-", "ddd".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new CallBackImpl(), "I am back 2!");
		zoo.create("/zk-test7-asyanc-", "qqq".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, new CallBackImpl(), "I am back 3!");
		Thread.sleep(10000);
	}

	@Override
	public void process(WatchedEvent e) {
		System.out.println("e==="+e.getState());
		if(KeeperState.SyncConnected == e.getState()){
			count.countDown();
		}
	}
}
class CallBackImpl implements AsyncCallback.StringCallback{
	@Override
	public void processResult(int i, String s, Object obj, String s1) {
		System.out.println("回调成功:i="+i+"	s="+s+"	obj="+obj+"		s1="+s1);
		
	}
	
}
