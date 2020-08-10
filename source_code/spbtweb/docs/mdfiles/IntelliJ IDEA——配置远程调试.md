# 配置步骤
1. 开启服务器端的远程调试，配置远程调试端口
2. 配置IDEA远程调试
3. 启动IDEA的debug
# 远端开启远程调试
cmd:
```shell
nohup java -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=8888,server=y,suspend=n -jar xspch.jar 2>&1 &
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190509165101948.png)
# IDEA配置
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190509165013868.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1NvdWxfUHJvZ3JhbW1lcl9Td2g=,size_16,color_FFFFFF,t_70)
控制台出现如下图日志，即表示连接成功
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190509165210389.png)