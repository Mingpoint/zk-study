package lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class ZkConnetionss implements Runnable{
	public void createZNode(ZooKeeper zk,String path,String  data) throws Exception{
		zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	}
	@Override
	public void run() {
	}
	public void createZNodePath(ZooKeeper zk,String path,String  data) throws Exception{
		zk.create(path, data.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	
	

}
