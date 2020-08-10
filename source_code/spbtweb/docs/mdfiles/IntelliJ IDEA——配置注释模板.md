### Settings中
File > Other Settings > Default Settings … > Editor > File and Code Templates
### 配置方式
![在这里插入图片描述](https://img-blog.csdnimg.cn/20190330171531105.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1NvdWxfUHJvZ3JhbW1lcl9Td2g=,size_16,color_FFFFFF,t_70)

也可以写死：

```
/**
*
* @author yourname
* @date ${DATE} ${TIME}
*/
```

```
In this template, you can specify a code fragment to be included into file templates (Templates tab) with the help of the #parse directive. One template can be shared by several file templates. Along with static text, code and comments, you can also use predefined variables that will then be expanded like macros into the corresponding values. 
Predefined variables will take the following values:
${PACKAGE_NAME}
 
name of the package in which the new file is created
${USER}
 
current user system login name
${DATE}
 
current system date
${TIME}
 
current system time
${YEAR}
 
current year
${MONTH}
 
current month
${MONTH_NAME_SHORT}
 
first 3 letters of the current month name. Example: Jan, Feb, etc.
${MONTH_NAME_FULL}
 
full name of the current month. Example: January, February, etc.
${DAY}
 
current day of the month
${DAY_NAME_SHORT}
 
first 3 letters of the current day name. Example: Mon, Tue, etc.
${DAY_NAME_FULL}
 
full name of the current day. Example: Monday, Tuesday, etc.
${HOUR}
 
current hour
${MINUTE}
 
current minute
${PROJECT_NAME}
 
the name of the current project
 Apache Velocity template language is used
```
### 最后新建一个class文件，即可验证是否生效（无需重启IDEA）