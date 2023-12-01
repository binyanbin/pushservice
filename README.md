基于netty推送实现,mysql做信息存储,协议简单

自用推送功能,满足自己业务功能,方便定制,推送流程如下:

![architecture](https://gitee.com/yanbin_yb/pushservice/raw/master/push/流程.png)

协议定义如下:

![architecture](https://gitee.com/yanbin_yb/pushservice/raw/master/push/协议.png)

类型1:客户端心跳消息<BR>
类型2:服务端响应消息(无信息)<BR>
类型3:服务端响应消息(有信息)<BR>
类型4:服务端关闭链接消息<BR>
类型5:客户端注册消息(内容为客户端注册id,32位字符串)<BR>
类型6:业务系统推送请求消息((内容为客户端注册id,32位字符串)<BR>




