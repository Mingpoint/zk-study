package config;

import org.I0Itec.zkclient.ZkClient;

public class ZKUtil {
	public static final String CONFIG_NODE_NAME = "/config/jdbc";
	 
    public static ZkClient getZkClient() {
        return new ZkClient("192.168.73.128:2181,192.168.73.128:2182,192.168.73.128:2183");
    }
}
