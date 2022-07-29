## Docker搭建

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
docker rmi image_name:version || image_id

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


## MySQL集群搭建

### 1. MySQL安装

**docker启动MySQL进程时需要间隔十多秒再启动下一个，不然会失败**

```bash
# 创建MySQL网段
docker network create --subnet 172.18.0.0/18 shopzz-net

# MySQL-001
docker run --name mysql-001 \
--net shopzz-net --ip 172.18.0.2 \
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
--net shopzz-net --ip 172.18.0.3 \
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
--net shopzz-net --ip 172.18.0.4 \
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

# MySQL-tx
docker run --name mysql-tx \
--net shopzz-net --ip 172.18.0.5 \
-m 400m \
-v /opt/data/docker/mysql-003/config:/etc/mysql/conf.d \
-p 3304:3306 \
-e MYSQL_ROOT_PASSWORD=123456 \
-e TZ=Asia/Shanghai \
--privileged=true \
-d mysql \
--character-set-server=utf8mb4 \
--collation-server=utf8mb4_unicode_ci \
--lower_case_table_names=1

```

### 2. ShardingSphere-Proxy安装

创建目录`/opt/data/docker/shardingsphere-proxy/conf`，并在其下新增如下文件：

文件模板地址：https://github.com/apache/shardingsphere/tree/master/shardingsphere-proxy/shardingsphere-proxy-bootstrap/src/main/resources/conf


目前仅新增如下两个文件：

server.yaml
```yaml
rules:
 - !AUTHORITY
   users:
     - root@%:123456
     - sharding@:123456
   provider:
     type: ALL_PRIVILEGES_PERMITTED
 - !TRANSACTION
   defaultType: XA
   providerType: Atomikos
```

config-sharding.yaml
```yaml
schemaName: shopzz

dataSources:
 rep_s1_member:
   url: jdbc:mysql://172.18.0.2:3306/shopzz-member?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
   minPoolSize: 1
 rep_s2_member:
   url: jdbc:mysql://172.18.0.3:3306/shopzz-member?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
   minPoolSize: 1
 rep_s3_member:
   url: jdbc:mysql://172.18.0.4:3306/shopzz-member?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true
   username: root
   password: 123456
   connectionTimeoutMilliseconds: 30000
   idleTimeoutMilliseconds: 60000
   maxLifetimeMilliseconds: 1800000
   maxPoolSize: 50
   minPoolSize: 1

rules:
- !SHARDING
 tables:
   ums_member:
     actualDataNodes: rep_s${1..3}_member.ums_member
     databaseStrategy:
       standard:
         shardingColumn: id
         shardingAlgorithmName: shopzz-inline
     keyGenerateStrategy:
       column: id
       keyGeneratorName: snowflake
   
   ums_address:
     actualDataNodes: rep_s${1..3}_member.ums_address
     databaseStrategy:
       standard:
         shardingColumn: member_id
         shardingAlgorithmName: shopzz-children-inline
     keyGenerateStrategy:
       column: id
       keyGeneratorName: snowflake

 bindingTables:
   - ums_member
   - ums_address

 defaultDatabaseStrategy:
   standard:
     shardingColumn: id
     shardingAlgorithmName: database_inline
  
 defaultTableStrategy:
   none:
 
 shardingAlgorithms:


   shopzz-inline:
     type: INLINE
     props:
       algorithm-expression: rep_s${(id % 3)+1}_member
   shopzz-children-inline: 
     type: INLINE
     props:
       algorithm-expression: rep_s${(customer_id % 3)+1}_member

 keyGenerators:
   snowflake:
     type: SNOWFLAKE
     props:
       worker-id: 123
```


使用Docker拉取ss-proxy镜像，并运行。

**运行时需要将mysql.jar驱动包映射到容器的lib目录下**

```bash
docker pull apache/shardingsphere-proxy

docker run --name ss-proxy -d \
--net shopzz-net --ip 172.18.0.6 \
-v /opt/data/docker/shardingsphere-proxy/conf:/opt/shardingsphere-proxy/conf \
-v /opt/data/docker/shardingsphere-proxy/lib/mysql.jar:/opt/shardingsphere-proxy/lib/mysql.jar \
-e PORT=3308 -p13308:3308 \
apache/shardingsphere-proxy:latest
```

通过数据库连接ss-proxy服务，可以进行代理访问MySQL集群了。




### 3. Redis安装

创建redis.conf配置文件到/opt/data/docker/redis/conf中。
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
docker run -v /opt/data/docker/redis/conf:/usr/local/etc/redis -p6379:6379 --net shopzz-net --ip 172.18.0.7 --name shopzz-redis -d redis
```


### 4.MinIO安装

执行命令创建容器
```bash
docker run -p 9000:9000 -p 9090:9090 \
--name shopzz-minio \
-m 400m \
--net shopzz-net --ip 172.18.0.8 \
-d --restart=always \
-e "MINIO_ACCESS_KEY=admin" \
-e "MINIO_SECRET_KEY=admin123456" \
-v /opt/data/docker/minio/data:/data \
-v /opt/data/docker/minio/config:/root/.minio \
minio/minio server /data --console-address ":9090" -address ":9000"
```

访问 http://ip:9090/dashboard 预览控制台


### 5.Nacos安装
```bash
docker run --name nacos -e MODE=standalone -p 8848:8848 -d nacos/nacos-server:1.1.4
```

通过 http://ip:8848/nacos/ 访问nacos控制台


### 6.sentinel-dashboard安装
```bash
docker run --name sentinel -p 8858:8858 -d bladex/sentinel-dashboard
```

通过http://ip:8848/nacos/访问nacos控制台

### 7.seata-server安装
```bash
docker run --name seata-server -p 8091:8091  -e SEATA_IP=host_ip -d seataio/seata-server

# 测试
yum install -y telnet
telnet localhost 8091
```

### 8.RocketMQ安装
```bash
# 安装name server
docker run --name namesrv -p 9876:9876 -e "MAX_POSSIBLE_HEAP=100000000" -d rocketmqinc/rocketmq sh mqnamesrv

# 安装broker server，创建配置文件
mkdir -p /usr/local/rocketmq
cd /usr/local/rocketmq
vi broker.conf

# broker.conf配置内容如下，需要修改机器IP
brokerClusterName = DefaultCluster
brokerName = broker-a
brokerId = 0
deleteWhen = 04
fileReservedTime = 48
brokerRole = ASYNC_MASTER
flushDiskType = ASYNC_FLUSH
brokerIP1 = ip

# 安装broker
docker run --name broker  -p 10911:10911 -p 10909:10909 -v  /usr/local/rocketmq/broker.conf:/opt/rocketmq-4.4.0/conf/broker.conf  --link namesrv:namesrv -e "NAMESRV_ADDR=namesrv:9876" -e "MAX_POSSIBLE_HEAP=200000000" -d rocketmqinc/rocketmq:4.4.0 sh mqbroker -c /opt/rocketmq-4.4.0/conf/broker.conf


# 安装console，需要修改机器IP
docker run --name=rocketmq-console -e "JAVA_OPTS=-Drocketmq.namesrv.addr=ip:9876 -Dcom.rocketmq.sendMessageWithVIPChannel=false" -p 8080:8080 -d styletang/rocketmq-console-ng
```


### 9.Kafka安装

```bash
# 拉取zk和kafka镜像
docker pull wurstmeister/zookeeper  
docker pull wurstmeister/kafka  

# 启动zk
docker run --name zz-zk -d \
--net shopzz-net --ip 172.18.0.9 \
-p 2181:2181 \
-t wurstmeister/zookeeper

# 启动kafka，--net指定docker网络，ip依次累加，KAFKA_BROKER_ID依次累加，KAFKA_ZOOKEEPER_CONNECT配置上一步ZK的IP与端口
# KAFKA_ADVERTISED_LISTENERS 配置为生产者可以访问的IP, 同内网环境可配置宿主机IP，外网环境测试配置外网IP。
docker run -d --name kafka-0 \
--net shopzz-net --ip 172.18.0.10 \
-p 9092:9092 \
-e KAFKA_BROKER_ID=0 \
-e KAFKA_ZOOKEEPER_CONNECT=172.18.0.9:2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://172.18.0.10:9092 \
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
-t wurstmeister/kafka

docker run -d --name kafka-1 \
--net shopzz-net --ip 172.18.0.11 \
-p 9093:9093 \
-e KAFKA_BROKER_ID=1 \
-e KAFKA_ZOOKEEPER_CONNECT=172.18.0.9:2181 \
-e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://172.18.0.11:9093 \
-e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9093 \
-t wurstmeister/kafka

docker run -d --name kafka-2 \
--net shopzz-net --ip 172.18.0.12 \
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