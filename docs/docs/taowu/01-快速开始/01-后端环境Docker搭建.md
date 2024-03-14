---
title: 后端环境Docker搭建
author: whoiszxl
date: 2023/02/23 12:28
editLink: false
categories:
 - 
tags:
---

# 测试环境手动搭建

## Docker环境搭建

### 1. 移除之前的docker相关包，非初始化环境执行
```sh
sudo yum remove docker \
docker-client \
docker-client-latest \
docker-common \
docker-latest \
docker-latest-logrotate \
docker-logrotate \
docker-engine
```

### 2. 配置阿里云yum源 (可选)
```shell
sudo yum install -y yum-utils

sudo yum-config-manager \
--add-repo \
http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```

### 3. 安装Docker
```bash
sudo yum install -y docker-ce docker-ce-cli containerd.io
```

### 4. 启动Docker
```bash
systemctl enable docker --now
```

### 5. 国内配置加速
* 访问[阿里云获取镜像加速配置地址](https://cr.console.aliyun.com/cn-hangzhou/instances/mirrors)，获取到加速器地址，按照操作文档进行对应操作。

```bash
sudo mkdir -p /etc/docker

# 需要替换registry-mirrors中的加速器地址
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://xxxxx.mirror.aliyuncs.com"]
}
EOF

sudo systemctl daemon-reload
sudo systemctl restart docker
```

### 6. Docker基础操作
```bash
# 拉取镜像 - 镜像名:版本名（标签）
docker pull nginx:1.20.1

# 查看本地镜像
docker images

# 删除本地镜像
docker rmi image_name || image_id

# 启动容器
docker run -name=my_nginx -d --restart=always -p 81:80 nginx

# 查看正在运行的容器
docker ps

# 查看所有
docker ps -a

# 删除停止的容器，-f强制删除
docker rm  container_id || container_name
docker rm -f container_id || container_name

# 停止容器
docker stop container_id || container_name

# 启动容器
docker start container_id || container_name

# 应用开机自启
docker update container_id || container_name --restart=always

# 进入容器内部
docker exec -it container_id /bin/bash

# 查看日志
docker logs container_id
```


## MySQL环境搭建

机器内存不够，可以只运行一个MySQL容器，在单个MySQL容器下创建多个库来模拟分库场景。

### 1. MySQL安装
:::tip 提示
docker启动MySQL进程时需要间隔十多秒再启动下一个，不然会失败
:::


```bash
# 创建MySQL网段
docker network create --subnet 172.18.0.0/18 taowu-net

# MySQL-001
docker run --name mysql-001 \
--net taowu-net --ip 172.18.0.1 \
-m 400m \
-v /opt/data/docker/mysql-001/config:/etc/mysql/conf.d \
-p 3301:3306 \
-e MYSQL_ROOT_PASSWORD=123456 \
-e TZ=Asia/Shanghai \
--privileged=true \
-d mysql \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci \
--lower_case_table_names=1

# MySQL-002
docker run --name mysql-002 \
--net taowu-net --ip 172.18.0.2 \
-m 400m \
-v /opt/data/docker/mysql-002/config:/etc/mysql/conf.d \
-p 3302:3306 \
-e MYSQL_ROOT_PASSWORD=123456 \
-e TZ=Asia/Shanghai \
--privileged=true \
-d mysql \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci \
--lower_case_table_names=1

# MySQL-003
docker run --name mysql-003 \
--net taowu-net --ip 172.18.0.3 \
-m 400m \
-v /opt/data/docker/mysql-003/config:/etc/mysql/conf.d \
-p 3303:3306 \
-e MYSQL_ROOT_PASSWORD=123456 \
-e TZ=Asia/Shanghai \
--privileged=true \
-d mysql \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci \
--lower_case_table_names=1

```


## ShardingSphere-Proxy环境搭建

### 1. 创建两个配置文件
* 第一个：server.yml，点击[server.yml](https://github.com/apache/shardingsphere/blob/master/examples/shardingsphere-proxy-example/shardingsphere-proxy-boot-mybatis-example/src/main/resources/conf/server.yaml)查看模板。

users中配置可以访问proxy代理服务的用户名和密码，privilege的type中配置用户操作数据库的权限，ALL_PERMITTED为所有权限。
```yaml
authority:
  users:
    - user: root@%
      password: root
    - user: sharding@%
      password: sharding
  privilege:
    type: ALL_PERMITTED
```


* 第二个：config-sharding.yaml，用来配置分库分表的规则。此处模拟order库的分库分表。
```yaml
schemaName: taowu-order

# 创建一批数据源[ds0_order, ds1_order, ds2_order]
dataSources:
 ds0_order:
   url: jdbc:mysql://127.0.0.1:3300/taowu-order-000?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true   
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
   minPoolSize: 1
 ds1_order:
   url: jdbc:mysql://127.0.0.1:3300/taowu-order-001?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true   
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
   minPoolSize: 1
 ds2_order:
   url: jdbc:mysql://127.0.0.1:3300/taowu-taowu-002?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true   
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
   minPoolSize: 1

# 此处定义sharding分片的规则
rules:
- !SHARDING
 tables:
   oms_order: # 定义关联[oms_order_0, oms_order_1, oms_order_2]三个表的逻辑表名称
     actualDataNodes: ds${0..2}_order.oms_order_${0..2} # 定义一个数据节点，这个数据节点的规则是ds0到ds1，再到ds2,这是一个范围的表达式，后面的oms_order也是一样，表示可以是oms_order_0,oms_order_1,oms_order_2。
     tableStrategy:
       standard: # 配置一个策略
          shardingColumn: id # 表明要按照id字段来进行分片
          shardingAlgorithmName: taowu-inline # 定义taowu-inline规则，下方有定义
     keyGenerateStrategy: # 主键的生成策略，这里使用雪花算法。因为多库多表用自增id的话，会造成id重复。
       column: id
       keyGeneratorName: snowflake
   
 bindingTables: # 绑定表名，把oms_order表名加进去。
   - oms_order
 defaultDatabaseStrategy: # 数据库路由策略
   standard:
     shardingColumn: member_id # 分片列名为member_id
     shardingAlgorithmName: database_inline # 指定了一个database_inline的策略，这个策略在下面定义。

 defaultTableStrategy:
   none:
 
 shardingAlgorithms:
   database_inline: # 数据库的分片策略，让member_id对3进行了一个取模
     type: INLINE
     props:
       algorithm-expression: ds${member_id % 3}_order

   taowu-inline:
     type: INLINE
     props:
       algorithm-expression: oms_order_${id % 3} # 表名路由规则，通过order表的主键id来对3进行取模计算得出来

# 定义雪花算法的一些参数，比如说worker-id
 keyGenerators:
   snowflake:
     type: SNOWFLAKE
     props:
       worker-id: 123
```

### 2. 运行shardingsphere-proxy服务
此处需要导入mysql的驱动包 `mysql.jar`
```bash
docker run --name ss-proxy -d \
-v /opt/data/docker/ss-proxy/conf:/opt/shardingsphere-proxy/conf \
-v /opt/data/docker/ss-proxy/lib/mysql.jar:/opt/shardingsphere-proxy/lib/mysql.jar \
-e PORT=3306 -p6666:3306 \
apache/shardingsphere-proxy:latest
```


## Redis环境搭建
创建redis.conf配置文件到/opt/data/docker/redis/conf中

```conf
# redis后台运行
daemonize yes

# 端口，默认6379 
port 6379

# 指定redis IP白名单
# 在生产环境中最好设置该项
# 测试环境直接允许所有IP请求
bind 0.0.0.0

# 客户端连接的超时时间，单位秒。
# 当客户端在这段时间内没有发出任何指令，那么关闭该连接
# 0:关闭此设置
timeout 0

# 日志记录级别，默认为verbose
# debug 记录很多信息，用于开发和测试
# varbose 有用的信息，不像debug会记录那么多
# notice 普通的verbose，常用于生产环境
# warning 只有非常重要或者严重的信息会记录到日志
loglevel debug

# 数据库数量
# 默认值为16，默认数据库为0
databases 16

# save 900 1  900秒内至少有1个key被改变（会在900秒的时候执行rdb同步）
# save 300 10  300秒内至少有300个key被改变（会在900秒的时候执行rdb同步）
# save 60 10000  60秒内至少有10000个key被改变 （会在60秒的时候执行rdb同步）
save 900 1
save 300 10
save 60 10000

# rdb文件是否压缩数据，默认为yes
rdbcompression yes

# RDB后本地持久化数据库文件名
dbfilename dump.rdb
 
# 工作目录
dir ./

appendonly yes
appendfsync everysec
no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb

# redis执行超过多少微秒将记录
# 负数将关闭慢日志，0为记录每个命令
slowlog-log-slower-than 10000

# 慢日志的最大长度
slowlog-max-len 1024
```

创建容器
```bash
docker run -v /opt/data/docker/redis/conf:/usr/local/etc/redis \
-p6379:6379 \ 
--name taowu-redis \
-d redis
```


## Nacos环境搭建

创建容器
```bash
docker run \
--name taowu-nacos \
-p 8848:8848 \
-p 9848:9848 \
-e MODE=standalone \
-e TIME_ZONE='Asia/Shanghai' \
-d nacos/nacos-server:v2.1.1
```

访问页面 http://127.0.0.1:8848/nacos，默认用户名密码皆为`nacos`。

## MinIO环境搭建

创建容器
```bash
docker run -p 9000:9000 -p 9090:9090 \
--name taowu-minio \
-m 400m \
-d --restart=always \
-e "MINIO_ACCESS_KEY=admin" \
-e "MINIO_SECRET_KEY=admin123456" \
-v /opt/data/docker/minio/data:/data \
-v /opt/data/docker/minio/config:/root/.minio \
minio/minio server /data --console-address ":9090" -address ":9000"
```

访问页面 http://127.0.0.1:9090/dashboard 预览控制台



## Sentinel环境搭建

创建容器
```bash
docker run --name sentinel -p 8858:8858 -d bladex/sentinel-dashboard
```


## RocketMQ环境搭建

安装Name Server
```bash
docker run --name namesrv -p 9876:9876 -e "MAX_POSSIBLE_HEAP=100000000" -d rocketmqinc/rocketmq sh mqnamesrv
```

安装Broker Server
```bash
mkdir -p /usr/local/rocketmq
cd /usr/local/rocketmq
vi broker.conf
```

broker.conf内容
```conf
brokerClusterName = DefaultCluster
brokerName = broker-a
brokerId = 0
deleteWhen = 04
fileReservedTime = 48
brokerRole = ASYNC_MASTER
flushDiskType = ASYNC_FLUSH
brokerIP1 = ip #【此IP需要修改】
```

安装Broker
```bash
docker run --name broker \
-p 10911:10911 -p 10909:10909 \ 
-v /usr/local/rocketmq/broker.conf:/opt/rocketmq-4.4.0/conf/broker.conf \
--link namesrv:namesrv \
-e "NAMESRV_ADDR=127.0.0.1:9876" \
-e "MAX_POSSIBLE_HEAP=200000000" \
-d rocketmqinc/rocketmq:4.4.0 sh mqbroker \
-c /opt/rocketmq-4.4.0/conf/broker.conf
```


## Kafka环境搭建
```bash
# 拉取zk和kafka镜像
docker pull wurstmeister/zookeeper  
docker pull wurstmeister/kafka  

# 启动zk
docker run --name zz-zk -d \
--net taowu-net --ip 172.18.0.9 \
-p 2181:2181 \
-t wurstmeister/zookeeper

# 启动kafka，--net指定docker网络，ip依次累加，KAFKA_BROKER_ID依次累加，KAFKA_ZOOKEEPER_CONNECT配置上一步ZK的IP与端口
# KAFKA_ADVERTISED_LISTENERS 配置为生产者可以访问的IP, 同内网环境可配置宿主机IP，外网环境测试配置外网IP。
docker run -d --name kafka-0 \
--net taowu-net --ip 172.18.0.10 \
-p 9092:9092 \
-e KAFKA_BROKER_ID=0 \
-e KAFKA_ZOOKEEPER_CONNECT=172.18.0.9:2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://172.18.0.10:9092 \
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
-t wurstmeister/kafka

docker run -d --name kafka-1 \
--net taowu-net --ip 172.18.0.11 \
-p 9093:9093 \
-e KAFKA_BROKER_ID=1 \
-e KAFKA_ZOOKEEPER_CONNECT=172.18.0.9:2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://172.18.0.11:9093 \
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9093 \
-t wurstmeister/kafka

docker run -d --name kafka-2 \
--net taowu-net --ip 172.18.0.12 \
-p 9094:9094 \
-e KAFKA_BROKER_ID=2 \
-e KAFKA_ZOOKEEPER_CONNECT=172.18.0.9:2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://172.18.0.11:9094 \
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9094 \
-t wurstmeister/kafka


# 检查安装是否成功

# 查看容器状态
docker ps

# 进入容器开启控制台生产者发送消息测试
docker exec -it kafka-0 /bin/
/opt/kafka/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic testtopic

# 进入容器开启控制台消费者接口消息测试
docker exec -it kafka-1 /bin/bash
/opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test-topic --from-beginning

# Kafka可视化界面安装
docker pull vimagick/cmak

docker run -p 9000:9000 -e ZK_HOSTS="192.168.58.132:2181" --name docker_cmak -d vimagick/cmak

docker run -d --name kafka-ui \
--net wts-net --ip 172.18.0.13 \
-p 9000:9000 \
-e ZK_HOSTS="172.18.0.9:2181"
```


## ElasticSearch环境搭建

1. 拉取Elasticsearch镜像

```bash
docker pull elasticsearch:7.6.2
```

2. 创建并运行Elasticsearch容器

```bash 
docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.6.2
```
使用`-p` 参数将容器的9200和9300端口映射到宿主机,Expose出来给外部访问。`-e`参数设置discovery.type为single-node,配置为单节点模式。

这样Elasticsearch就可以通过http://localhost:9200访问了。

3. 修改内存设置

默认情况下,Elasticsearch使用2GB内存。可以通过指定ES_JAVA_OPTS环境变量来修改内存设置:

```bash
docker run -d -p 9200:9200 -p 9300:9300 -e ES_JAVA_OPTS="-Xms512m -Xmx512m" -e "discovery.type=single-node" elasticsearch:7.6.2
```
这将限制Elasticsearch使用512MB内存。


4. 测试访问

使用浏览器访问9200端口，http://IP:9200/，得到如下结果便说明Elasticsearch安装成功了。

```json
{
  "name": "dd743099319b",
  "cluster_name": "docker-cluster",
  "cluster_uuid": "9nuJhcy2T_O8vqsTkBRJJg",
  "version": {
    "number": "7.6.2",
    "build_flavor": "default",
    "build_type": "docker",
    "build_hash": "ef48eb35cf30adf4db14086e8aabd07ef6fb113f",
    "build_date": "2020-03-26T06:34:37.794943Z",
    "build_snapshot": false,
    "lucene_version": "8.4.0",
    "minimum_wire_compatibility_version": "6.8.0",
    "minimum_index_compatibility_version": "6.0.0-beta1"
  },
  "tagline": "You Know, for Search"
}
```


## LogStash环境搭建




## Kibana环境搭建




## Prometheus+Grafana环境搭建




## SkyWalking环境搭建



