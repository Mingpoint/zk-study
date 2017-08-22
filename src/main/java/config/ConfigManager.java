package config;

import org.I0Itec.zkclient.ZkClient;

public class ConfigManager {
	private Config config;
	/**
     * 模拟从db加载初始配置
     */
    public void loadConfigFromDB() {
        config = new Config("1111.1", "test", "123456");
    }
 
    /**
     * 模拟更新DB中的配置
     */
    public void updateconfigToDB(String url, String name, String password) {
        if (config == null) {
            config = new Config();
        }
        config.setUrl(url);
        config.setName(name);
        config.setPassword(password);
    }
 
    /**
     * 将配置同步到ZK
     */
    public void syncconfigToZk() throws Exception {
        ZkClient zk = ZKUtil.getZkClient();
        if (!zk.exists(ZKUtil.CONFIG_NODE_NAME)) {
            zk.createPersistent(ZKUtil.CONFIG_NODE_NAME, true);
        }
        zk.writeData(ZKUtil.CONFIG_NODE_NAME, config);
        zk.close();
    }
}
