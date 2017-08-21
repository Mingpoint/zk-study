package test.session;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class AuthSample{
	private static ZooKeeper zk = null;
	public static void main(String[] args) throws Exception {
		zk = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, null);
		zk.addAuthInfo("digest", "foo:true".getBytes());
		zk.create("/zk-test-auth", "123456".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
		byte[] data = zk.getData("/zk-test-auth", false, null);
		System.out.println();
//		Thread.sleep(Integer.MAX_VALUE);
		
		zk = new ZooKeeper("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183", 5000, null);
		byte[] data1 = zk.getData("/zk-test-auth", false, null);
		System.out.println(data1);
		
	}
}
