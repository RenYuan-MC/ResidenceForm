## 这是什么分支?
这是给网易的互通服设计的分支  
由于网易版MC并不使用floodgate而使用其独有的SpigotMaster, 故需单独兼容  
由于网易的互通同样使用Geyser, 基于Cumulus, 所以Form部分并不需要额外修改  
此分支不提供SpigotMaster, 无法直接构建, 请自行按照 [此处](https://mc.cab/163) 文档添加依赖并构建  
**已知问题: 不能正确接收关闭菜单的Response, 且无解决方案**

## ResidenceForm 领地Form支持
为领地添加基岩版Form菜单以让基岩版玩家可以更好的使用领地
## 使用方式
指令/rform或/res form打开领地菜单然后使用
## 已实现的功能
* 领地选择
* 领地公共权限管理 ( res set )
* 领地玩家管理 ( res pset )
* 领地信任玩家管理 ( res padd/pdel )
* 领地扩展/缩小 ( res expand/contract )
* 领地传送点设置 ( res tpset )
* 领地踢出 ( res kick )
* 领地传送 ( res tp )
* 领地删除 ( res remove )
* 领地转移 ( res give )
* 领地重命名 ( res rename )
* 领地边界查看 ( res show )
* 领地信息 ( res info )
* 领地创建自动选择 ( res select auto )
* 领地创建扩展/缩小 ( res select expand/contract )
* 领地创建 ( res create )
## 前置
* Floodgate (2.2.0版本以上) - 用于基岩版菜单
* Residence (5.0版本以上) - 领地插件
## 数据统计
![](https://bstats.org/signatures/bukkit/ResidenceForm.svg)
