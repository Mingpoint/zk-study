package config;

public class Test {
	  public static void main(String[] args) throws Exception {
		
	        ConfigManager cfgManager = new ConfigManager();
	        ClientApp clientApp = new ClientApp();
	        //模拟【配置管理中心】初始化时，从db加载配置初始参数
	        cfgManager.loadConfigFromDB();
	        //然后将配置同步到ZK
	        cfgManager.syncconfigToZk();
	        
	        
	        //模拟客户端程序运行
	        clientApp.run();
	        //模拟配置修改
	        cfgManager.updateconfigToDB("10.6.12.34", "newUser", "newPwd");
	        cfgManager.syncconfigToZk();
	        //模拟客户端自动感知配置变化
	        clientApp.run();
	    }
}
