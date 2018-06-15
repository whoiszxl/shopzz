# 先杀死端口为10001的java项目进程
kill -9 $(ps -aux | grep 10001 | awk '{print $2}')

# 进到项目拉取git最新代码
cd ~/AYANAMI && git pull origin master

# 然后重新构建项目
cd ~/AYANAMI/xlmall-api2.0/project_code/xlmall-api/ && /root/soft/apache-maven-3.2.2/bin/mvn clean package -Dmaven.test.skip=true

# 运行项目
cd ~/AYANAMI/xlmall-api2.0/project_code/xlmall-api/target && nohup java -jar xlmall-api-1.0.0.jar --server.port=10001 &
