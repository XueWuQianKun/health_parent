# health_parent
健康项目

1  自行修改jdbc.properties中的内容     注意: 提交时候"不要"提交这个文件

```java
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql:///health?useSSL=false&serverTimezone=Asia/Shanghai
name=root
password=liangyu123
```

2  父工程的版本  mysql 自己修改,  注意: 提交时候"不要"提交这个文件

```java
<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
<maven.compiler.source>1.8</maven.compiler.source>
<maven.compiler.target>1.8</maven.compiler.target>
<junit.version>4.12</junit.version>
<spring.version>5.0.2.RELEASE</spring.version>
<pagehelper.version>4.1.4</pagehelper.version>
<servlet-api.version>2.5</servlet-api.version>
<dubbo.version>2.6.0</dubbo.version>
<zookeeper.version>3.4.7</zookeeper.version>
<zkclient.version>0.1</zkclient.version>
<mybatis.version>3.4.5</mybatis.version>
<mybatis.spring.version>1.3.1</mybatis.spring.version>
<mybatis.paginator.version>1.2.15</mybatis.paginator.version>
<mysql.version>8.0.16</mysql.version>
<druid.version>1.0.9</druid.version>
<commons-fileupload.version>1.3.1</commons-fileupload.version>
<spring.security.version>5.0.5.RELEASE</spring.security.version>
<poi.version>3.14</poi.version>
<jedis.version>2.7.0</jedis.version>
<quartz.version>2.2.1</quartz.version>
```