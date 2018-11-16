# Explorer
SpringBoot 公众号后台

## 预览
![预览](https://raw.githubusercontent.com/fengjunren/fengjunren.github.io/master/dl/wx/preview.gif)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
![体验](https://raw.githubusercontent.com/fengjunren/fengjunren.github.io/master/dl/wx/%E6%B5%8B%E8%AF%95%E5%85%AC%E4%BC%97%E5%8F%B71.jpg)

## 功能介绍
* 微信消息推送
* rsa、aes 组合加密数据传输 (aes 加密数据内容, rsa 加密aes key)
* 前端Vue(https://github.com/fengjunren/explo-wx)

## 准备
* 云主机、域名
* 申请测试公众号 （https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login）
![1](https://raw.githubusercontent.com/fengjunren/fengjunren.github.io/master/dl/wx/testpublic2.png)
![2](https://raw.githubusercontent.com/fengjunren/fengjunren.github.io/master/dl/wx/templateId2.png)

## 初始化
* mysql 中执行 Explorer/initdb.sql
* wx_config表插入一条数据（wxId,appId,appSecret）  分别为上图的微信号、appID、appsecret

## 代码配置
* application.properties  (*号部分需要重新填写)

```
# 数据库连接相关配置
spring.datasource.url=jdbc:mysql://***?characterEncoding=utf8&useSSL=false
spring.datasource.driverClassName=com.mysql.jdbc.Driver
spring.datasource.username=***
spring.datasource.password=***

#设置hikari的数据库连接池名
spring.datasource.hikari.pool-name="cn.explo"

#mybatis 扫描的bean
mybatis.type-aliases-package=cn.explo.pojo

#日志管理
logging.level.root=ERROR
logging.level.org.springframework.web=ERROR
logging.level.cn.explo = debug
# 本地日志路径
#logging.file =log/explo.log 
# 远程服务器日志路径
logging.file =/data/wwwlogs/explo.log  
logging.pattern.console=%d{yyyy/MM/dd-HH:mm:ss} [%thread] %-5level %logger %n%msg%n
logging.pattern.file=%d{yyyy/MM/dd-HH:mm} [%thread] %-5level %logger %n%msg%n
 
```

* resources/config/config.properties  (*号部分需要重新填写)

```
# 自定义属性配置
wxconfig.wxId = ***
wxconfig.templateId=***

encrypt.rsa.publick_key = MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7/KyjEXEcjvZAXfbYqTU6E8kGA5OK0GvZz/s2kpwDDgkGK4u5YucFSqnH7xtDoaDlnyiW1sryaXKzzHZotZVgooEE86vYrGyeEA1LS0b7Wj2CxBS4h9W+9aZZZ70/622+wNSCp/Uf3aiTHXMD0pQW9qk3R826NqXhn0PSd6vqNwIDAQAB
encrypt.rsa.private_key = MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALv8rKMRcRyO9kBd9tipNToTyQYDk4rQa9nP+zaSnAMOCQYri7li5wVKqcfvG0OhoOWfKJbWyvJpcrPMdmi1lWCigQTzq9isbJ4QDUtLRvtaPYLEFLiH1b71pllnvT/rbb7A1IKn9R/dqJMdcwPSlBb2qTdHzbo2peGfQ9J3q+o3AgMBAAECgYAnXD5hXlOKoTxaOdJnhvZIFMBwa+tGk2Ehl/SHdqcH1LZ6/j8hL94jM4DdBel1fbAMpAiBnLOdkgqcLuFUB0EhQMZM/8Ze1Y/u8XdJMTjlXos8B/T3tz1qgbYaidMsoKJfiRZCdwfly8VbPBroZb5B6Lo5dlUbw+/a4hTnxDpmwQJBAN3IYYFUasRlPrQVhy8ZydwTRKuToFzfOCMmkc8tOFxDCsNny/eCOcbgrQQbS+6iR/B90judjf2SJq/EBNSUntECQQDY/Xvk5vVjhLH64HiLB6VlOqY2VeSPnE9kdCIxEEN8h37nkD5LqVJzQbRyQVvds4DpNm+FoRP83K3gnZTTmAqHAkBGoDccvks5u5waMPjZsPatVJ17ngllSdhQqUmplhbqmpbk3zr+2MOZZ5bxRdgKaDdMiGTYgzXiWBZlxqBbKfDhAkEAjpsxFIPRW+E2kzl5H24b/gZofCdA+jzxVISagAI/SBVIiukad+FE5aiV7c0UkgTJwPtcmcb9+t4RjCAkUleG+wJBAJAn1UBtxwakM3sc4aAI2IPUyOnhuq/9u4quL3+WG9Q898jvMUDZYrHMEtAgiJozSZ6Abpm3Z5dia2wrpwy/GgY=
 
backEndUrl=https://***/Explorer/
frontEndUrl=https://***/explo-wx/#/gateWay/
```

## 部署
* 本地运行(注释pom.xml中这两段) 
* tomcat中运行 打war包时(放开pom.xml中这两段)
* 打包命令 （mvnw clean package）
```
<!--  
<exclusions> <exclusion> <groupId>org.springframework.boot</groupId> 
<artifactId>spring-boot-starter-tomcat</artifactId> </exclusion> </exclusions> 
 -->
```
```
<!-- 
 <dependency> <groupId>javax.servlet</groupId> <artifactId>javax.servlet-api</artifactId> 
  <scope>provided</scope> </dependency> 
--> 
```
