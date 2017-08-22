zk client 命令：
创建：
1.create [-s ,-e] path data acl
其中-s表示创建一个顺序自动编号的节点,-e表示创建一个临时节点,默认为持久性节点
读取：
1.ls path [watch],查看path目录下的所有子节点
2.get path [watch],查指定节点的属性信息和数据内容
更新：
set path data [version],指定节点更新，version是这次更新基于那一个
ZNode节点的数据版本更新的
删除：
delete path [version],删除指定节点，version如果与当前的版本不一致删除不了
如果该节点下有子节点也是不能删除的

学习简单分布式锁的实现、简单配置中心实现


