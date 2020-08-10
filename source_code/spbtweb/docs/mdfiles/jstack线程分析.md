### 先附上jstack命令
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190321005629235.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1NvdWxfUHJvZ3JhbW1lcl9Td2g=,size_16,color_FFFFFF,t_70)
#### 1. 通过linux的top命令，显示当前活跃线程数，为CPU使用率降序排列
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190321004216376.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1NvdWxfUHJvZ3JhbW1lcl9Td2g=,size_16,color_FFFFFF,t_70)
#### 2. 使用jstack pid（示例：jstack 10420）命令查看java进程的堆栈状态
![在这里插入图片描述](https://img-blog.csdnimg.cn/2019032100490683.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1NvdWxfUHJvZ3JhbW1lcl9Td2g=,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/20190321004949843.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1NvdWxfUHJvZ3JhbW1lcl9Td2g=,size_16,color_FFFFFF,t_70)
#### 3. 通过thread dump分析线程状态
例如：jstack -F 10420
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190321010034155.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1NvdWxfUHJvZ3JhbW1lcl9Td2g=,size_16,color_FFFFFF,t_70)
大多数情况下会基于thead dump分析当前各个线程的运行情况，如是否存在死锁、是否存在一个线程长时间持有锁不放等等。

在dump中，线程一般存在如下几种状态：
1. RUNNABLE，线程处于执行中
2. BLOCKED，线程被阻塞
3. WAITING，线程正在等待

通过分析堆栈信息，定位线程卡在哪一个任务或操作上，从而定位到问题所在。