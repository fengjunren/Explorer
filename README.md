# Explorer
SpringBoot 公众号后台

## 预览
![预览](https://raw.githubusercontent.com/fengjunren/fengjunren.github.io/master/dl/wx/preview.gif)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
![体验](https://raw.githubusercontent.com/fengjunren/fengjunren.github.io/master/dl/wx/%E6%B5%8B%E8%AF%95%E5%85%AC%E4%BC%97%E5%8F%B71.jpg)

## 准备
* 云主机、域名
* 申请测试公众号 （https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login）
![1](https://raw.githubusercontent.com/fengjunren/fengjunren.github.io/master/dl/wx/testpublic2.png)
![2](https://raw.githubusercontent.com/fengjunren/fengjunren.github.io/master/dl/wx/templateId2.png)

## 初始化
* mysql 中执行 initdb.sql
* wx_config表插入一条数据（wxId,appId,appSecret）  分别为上图的微信号、appID、appsecret
