# 基于MQTT的聊天系统演示

## 1. 基本架构

- 通信协议 [MQTT](http://mqtt.org/)
- 消息代理 [Mosquitto](https://mosquitto.org/)
- 用户鉴权 [mosquitto-auth-plug](https://github.com/jpmens/mosquitto-auth-plug)
- 消息持久化 MySQL

## 2. 聊天协议

一个用户或一个群组对应一个主题: `/users/{userId}/{groupId}/messages`

- 用户必须订阅属于自己的主题，才能收到发送给自己的消息
- 用户要发送消息给其他用户，直接向其他用户的主题发布消息即可
- 用户要接收群组消息，必须订阅该群组对应的主题
- 用户要发布群组消息，直接向群组对应的主题发布消息即可

以用户`TRUMP`、`REAGAN`和群组`PRESIDENTS`为例

- SUB /users/TRUMP/messages 用户`TRUMP`订阅发送给自己的消息
- PUB /users/REAGAN/messages 用户`TRUMP`向用户`REAGAN`发布消息
- SUB /groups/PRESIDENTS/messages 用户`TRUMP`订阅群组`PRESIDENTS`的消息
- PUB /groups/PRESIDENTS/messages 用户`TRUMP`向群组`PRESIDENTS`发布消息

### 2.1 主题格式

- 用户消息 `/users/{userId}/messages`
- 群组消息 `/groups/{groupId}/messages`

### 2.2 消息格式

消息以JSON格式传送。消息字段:

- type:enum(CHAT|GROUPCHAT) 分单聊、群聊
- fromId:int 发送方用户ID
- toId:int 接收方用户ID或群组ID
- content:text 消息文本

## 3. 消息持久化

使用超级用户连接MQTT服务器，订阅全部消息，将其中的单聊消息与群聊消息存储入库

## 4. 用户鉴权

`Mosquitto`只提供了简单的文本文件密码鉴权，因此，需要开发或使用扩展插件来自定义鉴权逻辑

常用的`Mosquitto`鉴权插件是`mosquitto-auth-plug`

### 4.1 鉴权协议选择

`mosquitto-auth-plug` 支持MySql鉴权，但是只支持`PBKDF2`哈希后的密码，跟当前数据库存储的密码格式不一致，只能弃用。
因此，选用HTTP鉴权，在HTTP服务端进行鉴权逻辑处理

### 4.2 鉴权插件的安装

此鉴权插件只提供源码版，因此，需要进行手动编译。

#### 4.2.1 编译前的准备:

1. C语言编译环境 包括 `clang|gcc` 、 `make` 等
2. `mosquitto-auth-plug` 源码。 Github克隆即可
3. `mosquitto` 源码。 官网下载或Github克隆都行
4. 本机安装`mosquitto` 。用来提供`mosquitto`头文件。这些头文件`mosquitto`源码中都有，不知为何要再次提供
5. 本机安装`openssl`。用来提供`openssl`运行时和头文件

#### 4.2.2 编译插件

1. 编译配置文件是 `${mosquitto-auth-plug}/config.mk`。官方提供模板文件`config.mk.in`。拷贝并命名为`config.mk`即可。
2. 编辑配置文件，指定鉴权协议，指定所需第三方库的路径
3. 执行命令`make`。如无异常，几秒后可编译成功，在当前目录生成插件`auth-plug.so`

示例参见附件1与附件2

### 4.2.3 配置`mosquitto`使用此插件

需要指定:

- auth_plugin 插件路径
- auth_opt_backends 认证协议
- auth_opt_http_ip 认证服务器IP
- auth_opt_http_port 认证服务器端口
- auth_opt_http_getuser_uri 用户认证接口
- auth_opt_http_superuser_uri 超级用户检查接口
- auth_opt_http_aclcheck_uri 用户鉴权接口

示例参见附件3

### 附件1 mosquitto-auth-plug通用编译配置

```log
BACKEND_CDB ?= no
BACKEND_MYSQL ?= no
BACKEND_SQLITE ?= no
BACKEND_REDIS ?= no
BACKEND_POSTGRES ?= no
BACKEND_LDAP ?= no
BACKEND_HTTP ?= yes
BACKEND_JWT ?= no
BACKEND_MONGO ?= no
BACKEND_FILES ?= no
BACKEND_MEMCACHED ?= no
MOSQUITTO_SRC =  /usr/local/Cellar/mosquitto/1.5.4
OPENSSLDIR = /usr/local/opt/openssl
SUPPORT_DJANGO_HASHERS ?= no
CFG_LDFLAGS = -undefined dynamic_lookup  -L/usr/local/Cellar/openssl/1.0.2l/lib
CFG_CFLAGS = -I/usr/local/Cellar/openssl/1.0.2l/include -I/usr/local/Cellar/mosquitto/1.4.12/include
```

### 附件2 mosquitto-auth-plug在macOS上的编译配置

```log
BACKEND_CDB ?= no
BACKEND_MYSQL ?= no
BACKEND_SQLITE ?= no
BACKEND_REDIS ?= no
BACKEND_POSTGRES ?= no
BACKEND_LDAP ?= no
BACKEND_HTTP ?= yes
BACKEND_JWT ?= no
BACKEND_MONGO ?= no
BACKEND_FILES ?= no
BACKEND_MEMCACHED ?= no
SUPPORT_DJANGO_HASHERS ?= no
CFG_LDFLAGS = -undefined dynamic_lookup
```

### 附件3 mosquitto鉴权插件配置

```log
auth_plugin /Users/yuchao/tmp/mosquitto-auth-plug/auth-plug.so
auth_opt_backends http
auth_opt_http_ip 127.0.0.1
auth_opt_http_port 8080
auth_opt_http_getuser_uri /mosquitto/auth
auth_opt_http_superuser_uri /mosquitto/superuser
auth_opt_http_aclcheck_uri /mosquitto/acl
```

## 协议

GPL-3.0
