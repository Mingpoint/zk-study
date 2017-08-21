package test.session;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class FindeZNodeListAsync implements Watcher{
	private static ZooKeeper zoo = null;
	private static CountDownLatch count = new CountDownLatch(1);

	public static void main(String[] args) {
		String path = "/zk-0p-psss";
		try {
			zoo = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, new FindeZNodeListAsync());
			count.await();
			zoo.create(path, "passs".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			zoo.create(path+"/c1", "pass".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			zoo.getChildren(path, true, new Children2CallImpl(), "I am callback!");
			
			zoo.create(path+"/c2", "pas".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
			Thread.sleep(Integer.MAX_VALUE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	@Override
	public void process(WatchedEvent en) {
		if(KeeperState.SyncConnected == en.getState()){
			if(EventType.None == en.getType() && null == en.getPath()){
				count.countDown();
			}else if(EventType.NodeChildrenChanged == en.getType()){
				zoo.getChildren(en.getPath(), true, new Children2CallImpl(), "I am callback!");
			}
		}
	}

}
class Children2CallImpl implements AsyncCallback.Children2Callback{

	@Override
	public void processResult(int arg0, String arg1, Object arg2,
			List<String> arg3, Stat arg4) {
		System.out.println("arg0="+arg0+"	arg1="+arg1+"	arg2="+arg2+"	arg3="+arg3+"	arg4="+arg4);
	}
}
