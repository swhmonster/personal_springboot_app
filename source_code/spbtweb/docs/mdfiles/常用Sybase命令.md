# Sybase命令

@[toc]
## 数据库配置

### 修改最大连接数为500
```
sp_configure 'number of user connections',500
go
```

### 查询设备数
```
sp_configure 'number of devices'
go
```

### 修改设备数为100
```
sp_configure 'number of devices'，100
go
```

### 缺省缓存分配页大小
```
sp_poolconfig 'default data cache','200M','16K'
go
```

### 最大锁数
```
sp_configure 'number of locks',100000
go
```

### 配置最大内存数
```
sp_configure 'max memory' ,2097151
go
```

### 配置最大在线CPU
```
sp_configure 'max online engines',4
go
alter thread pool syb_default_pool with thread count = 4
go
```

### 配置启动cpu个数
```
sp_configure 'number of engines at startup',4 
go
```

### 配置最大内存数
```sql
sp_configure 'max memory' ,2097151  --单位2k
go
```

### 分配最大存储过程缓存
```sql
sp_configure 'procedure cache size',102400  --单位2k
go
```

### 配置高速缓存
```
sp_cacheconfig 'default data cache' , '700M'
go
```

### 网络包大小
```
sp_configure 'max network packet size',1024
go
```

### 最大打开对象
```
sp_configure 'number of open object',9000
go
```

### 最大索引
```
sp_configure 'number of open indexes',10000
go
```

### 最大锁数
```
sp_configure 'number of locks',100000
go
```

### 增加网络内存
```
sp_configure 'additional network memory',1024
go
```

### 锁内存
```
sp_configure 'lock shared memory',512
go
```

### 优化tempdb
```
select dbid, name,segmap
from sysusages, sysdevices
where sysdevices.low <= sysusages.size +vstart
and sysdevices.high >=sysusages.size+vstart -1
and dbid =2
and (status=2 or status=3) 
go
use tempdb
go
sp_dropsegment 'logsegment', 'tempdb', 'master' 
go
sp_dropsegment 'system', 'tempdb', 'master' 
go
sp_dropsegment 'default', 'tempdb', 'master' 
Go

select dbid, name,segmap
from sysusages, sysdevices
where sysdevices.low <= sysusages.size +vstart
and sysdevices.high >=sysusages.size+vstart -1
and dbid =2
and (status=2 or status=3) 
go
sp_cacheconfig tempdb_cache, '100M'
go
sp_poolconfig tempdb_cache,'50M','16K'
go
sp_bindcache 'tempdb_cache',tempdb
go
sp_helpcache tempdb_cache
select name,id from syscharsets
dbcc traceon(3604)
dbcc memusage
```

## 数据库安装
### 数据库安装的优化
#### 应用数据库使用裸设备
若在UNIX（UNIXWARE）操 作系统下安装数据库服务器，请将SYBASE应用数据库的设备(device)安装成裸设备。即在创建应用数据库设备 （如：IVSP，DB160，NAP2000等）时用裸设备，把文件名指向 /dev/dsk/ 子目录下的相应文件。系统数据库设备（如:master等）仍然指向文件系统。

#### 补丁程序
完成SYBASE数据库的安装，请注意原版的SYBASE软件都会带有最新的补丁，一定要把补丁打上，否则会出现一些莫名其妙的问题。FOR NT 版的补丁是一个ZIP文件，解压至C:/SYBASE子目录即可。

#### 安装 SYBASE 在线帮助
在安装好 SYBASE 后，在安装目录下有一文件：../scripts/ins_syn_sql，在服务器上执行该脚本：
```
Sybase for Unix版：./isql -Usa -P -i../scripts/ins_syn_sql
Sybase for Winnt版：isql -Usa -P -i/sybase/scripts/ins_syn_sql
```
执行完毕后，即可在任意的 SYBASE 客户端上连接上 SQL SERVER ，在线取得任意命令的帮助：
```
sp_syntax "关键字"
如：sp_syntax "alter" 即可列出所有包含"alter"字符的命令
```

### 数据库配置的优化
#### 优化master数据库
首先加大master设备空间，初始默认为30M，加大为150M。然后加大master数据库空间，默认数据段和日志段各为5M大小，建议改为数据段100M，日志段50M：
```
alter database master on master=95
```

#### 优化tempdb数据库
首先创建tempdb设备，分配给tempdb数据库，默认tempdb数据库数据段和日志段各为2M大小，并创建在master设备上，建议改为数据段200M，日志段50M，创建在tempdb设备上：
```
alter database tempdb on tempdb=200
```
SQL SEVRER所有用户都共享工作表和临时表的tempdb数据库，tempdb主要瓶颈是磁盘I/0。解决办法是把tempdb放在更快的设备上。在 UNIX环境中，把tempdb放在文件系统中而不用原始的设备。由于tempdb在创建数据时，自动在master设备上创建为2M的数据库，为了减少 冲突，最好的办法是把tempdb从master设备中移走。为了达到上述目的，可采用如下办法实现：
- 在单用户状态下启动SQL SERVER，启动单用户方法：
```
dataserver -dmaster.dat -m
```
- 以sa登录
- 在文件系统中创建一个哑数据库
- 删除sysusages和 sysdatabase表中对现有tempdb数据库的引用
- 获取哑数据库的数据库ID，相应修改sysusages和 ysdatabase表对tempdb的引用
- 重新启动数据库

以在newdevice中创建200M的tempdb数据库为例，执行过程如下：
```
create database newtemp on newdevice=200
go /* 创建新的数据库 */
begin tran
go /* 开始事务，防止操作错误时破坏整个SQL SERVER的运行*/
delete sysusages where dbid = 2
delete sysdatabases where dbid = 2
go /*删除系统表对tempdb的引用，只能在单用户状态下执行*/
select dbid from sysdatabases where name = ‘newtemp’
go /*获取newtemp数据库ID，假定为10*/
update sysusages set dbid = 2 where dbid=10
update sysdatabases set name=’tempdb’,dbid=2 where name=’newtemp’
go /*修改数据库的引用，对newtemp的引用改为对tempdb的引用*/
select name,dbid, from sysdatabases where name = ‘tempdb’
select * from sysusages where dbid = 2
go /*测试修改是否正确，正确则提交，否则可用rollback回退*/
commit tran
go /*修改成功，重新启动系统*/
```
**注意：这种方法只对tempdb有效，其他数据库不能采用这种方法。因为在SQL SERVER启动时，tempdb每次都重新初始化。**

#### 优化系统参数
以下参数为标准建议值，可根据实际情况修改。
优化系统参数的SQL脚本注释
```
sp_configure 'total memory', 100000
```
优化数据库的内存，应根据不同机器配置设置, 建议为一半的物理内存大小。以db_block为单位，即每个单位为2k，上例为200M，默认为24M：
```
sp_configure "lock scheme" , 1,"datarows" // 系统默认为表级锁，优化为行锁
sp_configure "number of locks" , 10000 // 加大最大锁进程数, 默认为5000。
alter table table_name lock datarows // 如果设置一个已经存在的表(tabel)的锁方式为行锁
sp_configure 'procedure cache percent' ,30 // 缺省值：20 建议值：procedure使用频率高时采用较大的值，不超过30
sp_configure "number of user connections",100 // 最大用户连接数，默认为25，每个连接要占70k内存
sp_configure 'number of devices',20 // 将最大设备文件数据改为15个
sp_configure ‘number of Open databases’,20 // 最大打开数据库个数，对于需在一台数据库服务上打个多个数据库则需加大此参数，默认为15
Sp_configure ‘max online engines CPU’,2 // 缺省值：1 建议值：采用实际机器的CPU个数
Sp_configure ‘total data cache size’,60000 // 缺省值：0 建议值：使用SQL SERVER内存的30%
```
如果上述参数改动后SYBASE启动不正常，则可检查SYBASE的错误日志，把SYBASE.cfg中的相应选项修改为较小的值。
附：SYBASE SQL SERVER 内存的分配
- SQL SERVER 可执行代码        3-4M
- SQL SERVER 使用的静态内存        2.2-3.25M
- 用户可配置的参数所占用内存，以下示例(11.9.2版)：

|配置项|默认值|内存占用|
|--|--|--|
|用户连接数(user connections)|25|70k/个|
|打开的数据库数(open database)|12|60k/个|
|打开的对象数(open objects)|500|1k/个|
|打开的索引数(open indexs)|500|1k/个|
|锁数目(locks)|5000|0.1k/个|
|数据库设备数(data device)|10|0.5k/个|

- 剩余部份分配
过程缓存 ( 由 procedure cache percent 决定，默认值为 20% )
数据缓存 ( 默认值为减去1、2、3项的 80% )

#### 优化数据库系统属性
-方法一：在sybase center中选择数据库属性，将属性中options选项中的下列项目选中。
```
allow select into/bulk copy
truncate log on checkpoint
checkpoint on recovery
abort transction on full log
free space accounting
allow nulls by default
auto identity column in non-unquie index 
```
- 方法二：在SQLPLUS中执行下列SQL脚本，如：
```
sp_dboption mydb,"abort tran on log full",true（设定当数据库的日志空间满时，就终止该进程，使用 sa 用户）

sp_dboption mydb," trunc log on chkpt ",true
sp_dboption mydb," no chkpt on recovery",true
sp_dboption mydb," no free space acctg ",true
sp_dboption mydb,"allow nulls by default",true
sp_dboption mydb," auto identity dbo use only ",true
```

#### 创建阈值存储过程
可根据不同的应用修改以下脚本或创建多个阈值存储过程，并在配置阈值时指定相应的存储过程：
```
create procedure sp_thresholdaction 
@dbname varchar(30),
@segmentname varchar(30),
@free_space int,
@status int as dump transaction @dbname with no_log 
print "LOG DUMP: '%1!' for '%2!' dumped",@segmentname,@dbname
go
```

#### 配置多个阈值
- 方法一： 
打开 Sybase Central，双击相应数据库(database)的段 Segments ->; logsegment，在Thresholds 页面中可设置自动清除日志的阀值。其中有 Last Chance 的一行是系统默认的最后机会阀值，即系统日志空闲空间小于该值时为最后一次自动清除日志的机会。设置时阀值的大小可设为日志总空间大小的20%左右。
另外再增加多个阈值。
- 方法二：
    - 使用如下指令查出数据库中日志的容量（用页表示）
    ```
    select sum(size) from master..sysusages where dbid=db_id("database_name" and (segmap&4)=4
    ```
    - 使用sp_addthreshold增加新的阈值，大小为日志容量的50%,如上面语句显示值为2048
    ```
    sp_addthreshold database_name,logsegment,1024，proc_dump_display
    ```
**注意：因一个大事务时可能会越过当前的threshold，所以必须加多个threshold，
使用命令select @@thresh_hysteresis查看数据库的滞后值，如结果为64页，则下一个阈值设为"最近的阈值-（2*64）"，请在所设阈值再按这种原则各增加两个更小的阈值。**

### 索引的优化
在良好的数据库设计基础上，需高效地使用索引，并经常的维护索引，下文介绍关于索引的相关内容。
#### 创建索引
索引分为三类：聚簇索引(clustered indexes)、非聚簇索引(nonclustered indexes)、覆盖索引(covering indexes)
鉴于索引加快了查询速度，但减慢了数据更新速度的特点。可通过在一个段上建表，而在另一个段上建其非聚簇索引，而这两段分别在单独的物理设备上来改善操作性能。
create [unique][clustered|nonclustered] index index_name on table_name(column_name...)

#### 重建索引
随着数据行的插入、删除和数据页的分裂，有些索引页可能只包含几页数据，另外应用在执行大块I/O的时候，重建非聚簇索引可以降低分片，维护大块I/O的效率。重建索引实际上是重新组织B-树空间。在下面情况下需要重建索引：
- 数据和使用模式大幅度变化。
- 排序的顺序发生改变。
- 要进行大量插入操作或已经完成。
- 使用大块I/O的查询的磁盘读次数比预料的要多。
- 由于大量数据修改，使得数据页和索引页没有充分使用而导致空间的使用超出估算。
- dbcc检查出索引有问题。

当重建聚簇索引时,这张表的所有非聚簇索引将被重建.

#### 索引统计信息的更新
当在一个包含数据的表上创建索引的时候，SQL Server会创建分布数据页来存放有关索引的两种统计信息：分布表和密度表。优化器利用这个页来判断该索引对某个特定查询是否有用。但这个统计信息并不 动态地重新计算。这意味着，当表的数据改变之后，统计信息有可能是过时的，从而影响优化器追求最有工作的目标。因此，在下面情况下应该运行update statistics命令：
- 数据行的插入和删除修改了数据的分布。
- 对用truncate table删除数据的表上增加数据行。
- 修改索引列的值。

### 查询优化
#### NOT IN子句
不知大家是否喜欢使用‘NOT IN’这样的操作，如果是，那尽量使用(NOT) EXISTS 替代。
例子：

```
语句1
SELECT dname, deptno FROM dept WHERE deptno NOT IN (SELECT deptno FROM emp);
语句2
SELECT dname, deptno FROM dept WHERE NOT EXISTS (SELECT deptno FROM emp WHERE dept.deptno = emp.deptno);
```
明显的，2要比1的执行性能好很多，因为1中对emp进行了full table scan,这是很浪费时间的操作。而且1中没有用到emp的index，因为没有where子句。而2中的语句对emp进行的是range scan。

#### 海量查询
- 在海量查询时尽量少用格式转换。
```
如用
WHERE a.order_no = b.order_no
而不用
WHERE TO_NUMBER (substr(a.order_no, instr(b.order_no, '.') - 1)= TO_NUMBER (substr(a.order_no, instr(b.order_no, '.') - 1)
```

- 查询海量数据是可以使用optimizer hints，例如/*+ORDERED */
```
如用
SELECT /*+ FULL(EMP) */ E.ENAME FROM EMP E WHERE E.JOB = 'CLERK';
而不用
SELECT E.ENAME FROM EMP E WHERE E.JOB || '' = 'CLERK';
```

- 对于数据量较大和业务功能较复杂的系统，Sybase的默认参数根本无法满足需要，必须进行优化。系统数据库方面的优化从两方面进行，一个是调 整数据库系统的一些性能参数的值，另一个是应用程序的调优。应用程序的调优调整hsql和sql的写法，配合sql合理的建索引，这里主要对Sybase 系统一些基本的性能参数的调优进行一个小结：
    - 内存
    内存是对性能影响最大，也是最需要也是最难调优的地方。内存调优一定要，常用的需要调整的参数有：
    ```
    sp_configure 'max memory',XXXXXXX (设置为服务器内存的75%,重启生效)
    sp_configure 'allocate max shared mem',1 (启动的时候自动分配max memory指定的最大内存)
    sp_cacheconfig 'default data cache','1300M'(设置数据缓存为max memory的一半)
    sp_cacheconfig 'default data cache','cache_partition=2'
    sp_configure 'procedure cache size',102400 (过程高速缓存,通常是max mem20%-30%,这里是200M,在大量的执行sql的时候这个参数一定要调大)
    ```
    
    实际中遇到一个很头痛的问题， 32位Windows版本的Sybase 最大内存只能到搞到3G左右，"default data cache"的值超过 1500M Sybase实例就起不来了，导致服务器的16G内存形同虚设，所以如果大家的项目和我这个类似，服务器和操作系统由客户提供，还换不了的，内存不妨要求 个4G就够了，多了也浪费。

    - CPU
当服务器的CPU个数多于一个时，可以考虑多CPU。实际上对于OS会自动调度，设一下只不过是控制的更精确一点。实际需要根据CPU数来修改，若CPU=N，一般设置为N-1。设置这个参数，比如我的服务器8个CPU, 就像下面这样设置：
    ```
    sp_configure 'max online engines',7
    sp_configure 'number of engines at startup',7
    sp_configure 'max parallel degree',1 (并行的度，大于或等于1)
    sp_configure 'number of worker processes',7 (并行度＊并发连接数＊1.5倍)
    ```
    
    - 连接数(这个没什么说的，数量管够就可以，默认数为25，可根据应用需要来修改。)
```
    sp_configure 'number of user connections',600
    查询数据库死进程
    select * from master..syslogshold
```
    - 锁
    数据库的锁机制其实是一个比较复杂的话题，这里只能简单说一下。Sybase数据库系统两个级别的锁机制：所有页锁、数据页锁。所有页锁在当数据库加锁时，既锁数据页，也锁索引页；数据页锁当数据库加锁时，只锁数据页，不锁索引页。
    Sybase支持三种类型的锁: 数据表锁、数据页锁、数据行锁。 一些常用的调优命令和策略如下:
    ```
    sp_configure 'number of locks',50000 （设置锁的数量）
    ```
    系统设置时要把锁的数量设大一点，简单说就是要管够；如果需要节省空间，减少维护量，使用所有页锁机制；而如果需要加快速度，空间足够，使用数据页锁机制。
    ```
    sp_sysmon '00:10:00',locks （检测表的使用情况）
    ```
    当通过监测发现锁竞争超过15%时，首先修改加锁最重的表的锁机制，然后再把数据页锁设置为数据行锁。如果发现螺旋锁多，则为该表建立单独的命名缓存并对命名缓存进行分区。
    
    - I/O
数据库调优总的思路是尽量减少和分散物理I/O，尽量减少网络I/O。
    减少物理I/O的办法有： 在命名缓存中增加大块的I/O缓冲池，把数据分散到多个硬盘上,采用RAID技术,建立段，使一个表跨越多个硬盘等等，基本和其他的数据库软件调优一样。
    减少网络I/O的办法是采用大数据包。

    ```
    sp_configure "default network packet size",2048 设置网络传送包的大小(需要重启动)
    sp_configure "max network packet size",2048
    ```

## Sybase使用
### SQL Advantage使用
先点击server，里面点击connect连接到sybase服务端，开一个新窗口，写一些sql执行即可（ctrl+e）

### 启动和关闭数据库，查看表结构
- 看所有的库
```
sp_helpdb
```

- 使用一个库
```
use 库名（注意大小写）
```

- 查看用户下所有表名
```
select name from sysobjects where type='U'
或
sp_tables
```

- 查看表结构
```
sp_help 表名（注意大小写）
```

- 显示结果只显示一行
```
set rowcount 1
go
```

- 输入你想查询的sql语句，记得最后要
```
set rowcount 0
go
```

- 启动数据库
```
[sybase@rosan128main install]$ pwd
/opt/sybase/ASE-12_5/install
[sybase@rosan128main install]$ ./RUN_rosantech &
```

- 关闭数据库
```
先要看启动了什么服务
1> select srvname from sysservers
2> go
 srvname                        
 ------------------------------ 
 SYB_BACKUP                     
 loopback                       
 rosanljw_BS                    
 rosantech                      

(4 rows affected)

关闭备份服务
1> shutdown SYB_BACKUP
2> go

关闭
1> shutdown with nowait
2> go
```

### DML和DDL
```
--建表
use test
go

create table table1
(test varchar(10))
go

--仿照table1建立table2
select * from table1 into table2
go
--------------------------------------------------------------------------------------

--如果不能仿照需要修改一些参数
sp_helpdb db_name    --查看SELECT into选项是否开启，没开需要手动开启
go
 
use master
go

sp_dboption test,'select into',true        --（test为db_name）
go

checkpoint
go

use test
go

select * from table1 into table2
go
```

### 用户管理
- 创建登陆用户（login）
```
sp_addlogin user,user_password    --依次为用户名，密码
go
```

- 数据库添加用户
```
use YWST 
GO
sp_adduser user
GO
```

- 删除用户
```
sp_dropuser user
go
```

- 删除登录用户
```
sp_droplogin user
go
```

### Sybase ASE参数配置
- 服务器级配置
使用存储过程sp_configure配置ASE (和oracle的show parameter很像)
  
    - 语法
    ```
    sp_configure [configname[, configvalue] | group_name | non_unique_parameter_fragment]
    ```
    - 作用
      查询服务器运行的当前值，设置服务器运行参数
      用 "sp_configure mem" 可以查看带有mem关键字的参数的具体配置，然后用"sp_configure 参数, 值"来配置新
```
例1. 配置内存
1)数据库使用内存
指定ASE拥有的总的共享内存大小
sp_configure 'max memory',nnn(单位为2K）( HP平台推荐物理内存的75%，其他平台80%)
sp_configure 'allocate max shared mem',1（数据库服务启动时就分配内存）(推荐为1)
2)配置缺省数据缓存的大小和分区
sp_cacheconfig 'default data cache','xxxM'
sp_cacheconfig 'default data cache','cache_partition=n'
(根据CPU个数以及内存大小确定，需为2的N次方)
3)配置存储过程的缓存大小
sp_configure 'procedure cache size',nnn(单位为2K)

例2. 配置CPU
sp_configure‘max online engines’,n(cpu个数> 2时推荐CPU数目减1)
sp_configure‘number of engines at startup’,n
3. 其他
1). number of devices (用户自己确定)
指定ASE的可创建和打开的数据库设备的最大号数
2). number of locks (用户自己确定)
指定ASE可同时打开的锁的最大数目
3). number of open objects (推荐8000-20000)
ASE的对象描述的缓存数
4)number of open indexes (推荐2000-5000)
ASE的索引描述的缓存数
5) stack size (如果有超长SQL和多层嵌套，推荐*2)
数据库堆栈的大小
6). default character set id(推荐使用cp936 –id 171)
数据库服务器使用的字符集
7). allow updates to system tables
指定系统管理员是否可以更改系统表中的数据
8)row lock promotion HWM，row lock promotion LWM
行锁升级为表锁的阀值
9)page lock promotion HWM，page lock promotion LWM
页锁升级为表锁的阀值
10) lock scheme
缺省的对表加的锁类型(缺省为页锁)
```

- 数据库级配置
使用存储过程sp_dboption更改数据库的数据库选项
  
    - 语法sp_dboption[dbname, optname, {true | false}]
    - 作用
      罗列出所有的数据库选项
      更改某个数据库的数据库选项
    - 常见的数据库选项
    ```
    trunclog on chkpt自动清日志(不能再做增量备份)
    select into/bulkcopy允许快速BCP,selectinto
    ddlin tran允许事务中创建对象
    ```

修改完成以后可以通过sp_helpdb查看到
```
例子：
1> use master
2> go

1> sp_dboptionuserdb, "trunclog on chkpt", true
2> go
数据库‘userdb'的数据库选项'trunclog on chkpt'被打开。
在被改变数据库中运行CHECKPOINT命令。
(return status = 0)

1> use userdb
2> go
1> checkpoint
2> go
```

- 修改字符集为中文
    - 先进入到中文字符集的目录，把中文加载一下
    ```
    [sybase@rosan128main cp936]$ pwd
    /opt/sybase/charsets/utf8

    [sybase@rosan128main cp936]$ charset -Usa -P123456 -SNP binary.srt utf8
    Loading file 'binary.srt'.
    
    ```

Found a [sortorder] section.
    
This is Class-1 sort order.
    
Character set for the sort order is already in the Syscharsets table.
    
Finished loading file 'binary.srt'.
    
1 sort order loaded successfully
    ```
    
- 修改默认字符集为中文
    首先查一下默认的中文字符集的id
    ```sql
    [sybase_W@sybase utf8]$ isql -Usa -P123456 -SNP_W
    1> sp_helpsort --的缺省排序顺序和字符集
    2> go
    Collation Name                 Collation ID
     ------------------------------ ------------
     altdict                                  45
......中间省略
    Sort Order Description
    
     ------------------------------------------------------------------
     Character Set = 190, utf8		--utf8
         Unicode 3.1 UTF-8 Character Set
         Class 2 Character Set
     Sort Order = 45, altdict		--45
         Alternate (lower-case first) dictionary ordering
    (return status = 0)
    1> sp_configure 'default sortorder id',50	--需要重启生效，第一次重启会失败，再重启一次
    2> go
1> exit
    [sybase_W@sybase utf8]$ 
    ```
    
- 查看数据库信息
```
sp_helpdb        --(查看所有数据库信息)
go

sp_helpdb test         --(查看test数据库详细信息)
go
```

- 查看ASE的远程服务器
```
sp_helpserver
go
```

- 查看数据库对象的信息
```
sp_helpdevice      --设备信息
sp_helpdb          --所有数据库的信息
sp_helptext        --存储过程名字

sp_spaceused         --查看一个当前库(需要use 数据库)或表占所占用的空间
sp_recompile （+usertable）    --重新编译存储过程和触发器。与该表相关联的存储过程和触发器在第一次运行时，自动重新编译
```

- 常用dbcc命令
```
dbcctraceon(3604)                     --(随后的dbcc命令结果输出到屏幕)
dbcctraceon(3605)                     --(随后的dbcc命令结果输出到错误日志文件)
dbcccheckalloc[ (database_name[, fix | nofix] ) ]     --(检查分配页)
dbcccheckcatalog[ (database_name) ]             --(检查系统表)
dbcccheckdb[ (database_name[, skip_ncindex] ) ]     --(检查数据库)
dbccchecktable( { table_name| table_id}         --(检查表)
[, skip_ncindex] )
dbcctablealloc( { table_name| table_id}         --(检查表分配页)
[, { full | optimized | fast | null }
[, fix | nofix] ] ) |
```

- 常用系统表
```
sysdatabases        --记录所有数据库基本信息
sysusages        --数据库空间分配情况
sysdevices        --数据库设备信息
syslogins        --数据库服务器登录信息
sysusers        --数据库用户信息
sysobjects        --数据库对象表(U 用户表，P 存储过程)
sysprocesses        --进程表
```

- 日常常规维护(性能优化)
- 更新统计信息
```
update statistics (+usertable)    --不会对表上锁，不影响业务，但比较耗资源 (optdiag)
```

- 整理数据库垃圾空间
重建表的聚簇索引
对行锁表执行reorg
如何加快上述操作 (配置I/O，并行)
**注意：以上操作对表上锁，另外，必须有足够的空间来执行(剩余空间必须为最大表的1.2倍左右) (sp_spaceusedusertable)**

- 重新编译存储过程和触发器
```
sp_recompile (+usertable)    --与该表相关联的存储过程和触发器在第一次运行时，自动重新编译
```

### 数据库备份

系统管理员每天必须作数据库的备份

  - 备份的命令
```
dump database YWST to '/home/sybase/dump/YWST.dmp'  --此路径为数据库服务器上的真实路径
go
```

  - 恢复的命令
```
load database YWST from '/home/sybase/dump/YWST.dmp'
go
```

可以增加数据设备文件或者扩大已有设备文件的大小 

### 日常维护

- 碎片整理

  此操作会锁表，需要每月在空闲时间执行一次

```sql
reorg rebuild 表名
go
```

- 更新统计值

```sql
update statistics T_MS_AJ
go
```

### 常用的存储过程和命令

以下存储过程可作为日常问题排查用

```
sp_who            (查看用户进程)
sp_lock            (查看数据库锁状况)
sp_help            (查看对象信息)
sp_helpdb        (查看数据库信息)
sp_helpdevice        (查看设备信息)
sp_spaceused        (查看表占用的空间大小)
select @@version    (查看版本号)
dbcc sqltext(@spid)    (查看@spid执行的sql语句)
sp_showplan @spid,null,null,null        (查看@spid的执行计划)
```

- sp__getlock
```sql
--sp__getlock，查看数据库当前锁

use sybsystemprocs
go

if object_id('sp__getlock') is not null
    drop procedure sp__getlock
go

create procedure sp__getlock
as
begin
declare @temp_sql     varchar(500)
declare @sql          varchar(10000)
declare @unionsql     varchar(10000)
declare @unionsql2    varchar(10000)
declare @dbname       varchar(100)
declare dbname_cursor cursor for select name from master..sysdatabases
set @temp_sql = ' select id as objid,name as objname,db_id(''@dbname'') as dbid from @dbname..sysobjects '
set @sql = 'select lc.spid as spid,pr.spid as "进程ID" , '
            + ' pr.ipaddr as "IP地址", '
            + ' pr.program_name as "应用名称", '
            + ' pr.cmd AS "执行命令", '
            + ' db_name(lc.dbid) as "数据库名", '
            + ' obj.objname as "对象名", '
            + ' (case when lc.type = 1 then ''排他表锁'' '
            + '       when lc.type = 2 then ''共享表锁'' ' 
            + '       when lc.type = 3 then ''排他意向锁'' '
            + '       when lc.type = 4 then ''共享意图锁'' '
            + '       when lc.type = 5 then ''排他页锁'' '
            + '       when lc.type = 6 then ''共享页锁'' '
            + '       when lc.type = 7 then ''更新页锁'' '
            + '       when lc.type = 8 then ''排他行锁'' '
            + '       when lc.type = 9 then ''共享行锁'' '
            + '       when lc.type = 10 then ''更新行锁'' '
            + '       when lc.type = 11 then ''共享下一键锁'' '
            + '       when lc.type = 256 then ''锁阻塞另一进程'' '
            + '       when lc.type = 512 then ''请求锁'' '
            + ' end) as "锁类型名称", '
            + ' lc.type as "锁类型", '
            + ' pr.blocked as "阻塞进程ID",'
            + ' bl.program_name as "阻塞应用名称", '
            + ' bl.ipaddr as "阻塞IP地址" '
            + ' from master..syslocks lc  '
            + ' left join master..sysprocesses pr on lc.spid = pr.spid '
            + ' left join master..sysprocesses bl on bl.spid = pr.blocked '
            + ' left join ('
open dbname_cursor
    while @@sqlstatus = 0 
    begin
        fetch dbname_cursor into @dbname
        set @unionsql = @unionsql + str_replace(@temp_sql,'@dbname',@dbname)  
        set @unionsql = @unionsql + 'union all'
    end
    close dbname_cursor
    deallocate dbname_cursor
set @unionsql2 = substring(@unionsql,1,char_length(@unionsql) - 9)	
set @sql = @sql + @unionsql2 + ') obj on lc.id = obj.objid and lc.dbid = obj.dbid'
set @sql = 'select * from ('+ @sql +') t where t.spid !=@@spid'
execute(@sql)
end
go
```

- sp__getT50sql
```sql
--sp__getT50sql，查看数据库当前运行sql

use sybsystemprocs
go

if object_id('sp__getT50sql') is not null 
	drop procedure sp__getT50sql
go

create procedure sp__getT50sql
as
begin

select top 50 s.SPID,p.ipaddr,p.program_name,s.CpuTime,t.LineNumber,t.SQLText
from
    master..monProcessStatement s,
    master..monProcessSQLText t,
    master..sysprocesses p
where 
    s.SPID=t.SPID
    and s.SPID = p.spid
    and p.spid != @@spid
order by 
    s.CpuTime desc
end
go
```


- sp__addUserGrant
```sql
--sp__addUserGrant，批量给表赋权

IF OBJECT_ID ('dbo.sp__addUserGrant') IS NOT NULL
 DROP PROCEDURE dbo.sp__addUserGrant
GO

create procedure sp__addUserGrant
@userid    varchar(200),
@grant     varchar(200),
@type     varchar(200)

as

begin
    declare @temp_sql varchar(300)
    declare @sql varchar(300)
    declare @tabname varchar(300)

    declare tbname_cursor cursor for select name from sysobjects  where type = @type
    set @temp_sql = 'grant '+@grant+' on '     
    open tbname_cursor
    while @@sqlstatus =0 
    begin
        fetch tbname_cursor into @tabname
        set @sql =  @temp_sql + @tabname + " to  " + @userid
        execute(@sql)
    end
    close tbname_cursor
 deallocate tbname_cursor
end 
GO

--- 添加登录账号
sp_addlogin newUser,'qwer1234'
GO
--- 添加用户
use YWST 
GO
sp_adduser newUser
GO

--- 用户赋予权限 注意是用sa分别切换到 DB_ATY/YWST/JCSZ/YYFZ
use YWST
go
sp__addUserGrant newUser,'Select','U'
GO
sp__addUserGrant newUser,'Select','V'
GO
sp__addUserGrant newUser,'execute','P'
GO
```

- sp__getsession

```sql
--sp__getsession，查看当前连接应用名称、ip、连接数量

use sybsystemprocs
go

if object_id('sp__getsession') is not null
    drop procedure sp__getsession
go

create procedure sp__getsession
as

begin
    select program_name,ipaddr,count(program_name) from master..sysprocesses group by program_name,ipaddr order by program_name
end
go
```

- sp__getdbspaceinfo

```sql
--sp__getdbspaceinfo，列出各数据库数据空间、日志空间大小及剩余百分比

use sybsystemprocs 
go

if object_id('sp__getdbspaceinfo') is not null 
    drop procedure sp__getdbspaceinfo
go

create procedure sp__getdbspaceinfo
as

begin 
select 
    convert(char(16),db_name(data_segment.dbid)) as "库名",
    str(round(total_data_pages / ((1024.0 * 1024) / @@maxpagesize),2),10,2) "总数据空间(MB)",
    str(round(free_data_pages / ((1024.0 * 1024) / @@maxpagesize),2),10,2) "剩余数据空间(MB)",
    str(round(total_log_pages / ((1024.0 * 1024) / @@maxpagesize),2),10,2) "总日志空间(MB)",
    str(round(free_log_pages / ((1024.0 * 1024) / @@maxpagesize),2),10,2) "剩余日志空间(MB)",
    str( round(100.0 * free_data_pages / total_data_pages ,2),10,2) "剩余数据百分比%",
    str( round(100.0 * free_log_pages / total_log_pages,2),10,2) "剩余日志百分比%"
from
(select dbid,
        sum(size) total_log_pages,
        lct_admin('logsegment_freepages', dbid ) free_log_pages
        from master.dbo.sysusages
        where segmap & 4 = 4 
        group by dbid
) log_segment
,
(select dbid,
        sum(size) total_data_pages,
        sum(curunreservedpgs(dbid, lstart, unreservedpgs)) free_data_pages
        from master.dbo.sysusages
        where segmap <> 4	
        group by dbid 
) data_segment
where data_segment.dbid = log_segment.dbid 
order by str( round(100.0 * free_data_pages / total_data_pages ,2),10,2) asc
end

go
```

   
