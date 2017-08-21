package test.session;

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

public class FileZNodeDataList implements Watcher{
	private static ZooKeeper zoo = null;
	private static CountDownLatch count = new CountDownLatch(1);
	
	public static void main(String[] args) {
		String path = "/zk-0p-psss";
		try {
			zoo = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, new FileZNodeDataList());
			count.await();
			zoo.create(path, "passs".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			//获取数据
			zoo.getData(path, true, new getDataImpl(), "I'm callback 1");
			//更新数据
			zoo.setData(path, "xixixix".getBytes(), -1);
			Thread.sleep(Integer.MAX_VALUE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void process(WatchedEvent event) {
		System.out.println("===="+event.getState());
		if(KeeperState.SyncConnected == event.getState()){
			if(EventType.None == event.getType() && null == event.getPath()){
				count.countDown();
			}else if(EventType.NodeDataChanged == event.getType()){
				zoo.getData(event.getPath(), true, new getDataImpl(), "I'm callback");
			}
		}
	}
}
class getDataImpl implements AsyncCallback.DataCallback{

	@Override
	public void processResult(int arg0, String arg1, Object arg2, byte[] arg3,
			Stat arg4) {
		if(arg0 == 0){
			System.out.println("arg1="+arg1+"	arg2="+arg2+"	arg3="+arg3+"	arg4="+arg4);
			System.out.println("czxid="+arg4.getCzxid()+"	mzxid="+arg4.getMzxid()+"	version="+arg4.getVersion());
		}
	}
	
}
