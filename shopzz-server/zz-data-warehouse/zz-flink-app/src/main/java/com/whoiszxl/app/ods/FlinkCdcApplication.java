package com.whoiszxl.app.ods;

import com.alibaba.ververica.cdc.connectors.mysql.MySQLSource;
import com.alibaba.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.alibaba.ververica.cdc.debezium.DebeziumSourceFunction;
import com.whoiszxl.function.CustomDebeziumDeserializationSchema;
import com.whoiszxl.utils.KafkaUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * flink cdc 主程序
 *
 * 运行时需要在程序后添加数据库相关参数
 *
 * --hostname 47.103.198.122 --port 3306 --username shopzz \
 * --password ECmzyDLN7bRSKtab --databaseList shopzz --tableList all \
 * --dbTopic ods_db_log
 */
public class FlinkCdcApplication {

    public static final String DELIMITER = ",";

    public static String hostname;
    public static Integer port;
    public static String username;
    public static String password;
    public static String[] databaseList;
    public static String[] tableList;
    public static String dbTopic;
    public static String kafkaBrokers;

    public static void main(String[] args) {

        if(args == null || args.length == 0) {
            System.err.println("请配置数据库相关参数\n" +
                    "1. --hostname 数据库连接地址\n" +
                    "2. --port 端口号\n" +
                    "3. --username 用户名\n" +
                    "4. --password 密码\n" +
                    "5. --databaseList 数据库集合，多个数据库使用(" + DELIMITER + ")分割\n" +
                    "6. --tableList 表集合，参数为[all]则监听所有表，指定表名格式为：[dbName.tableName]，多个表使用(" + DELIMITER + ")分割\n" +
                    "7. --dbTopic 指定binlog日志同步到Kafka的哪个topic\n" +
                    "8. --kafkaBrokers kafka集群地址，地址需加端口地址，地址之间使用(\" + DELIMITER + \")分割\n");
            System.exit(0);
        }else {
            parseCommandLine(args, 0);
            parseCommandLine(args, 2);
            parseCommandLine(args, 4);
            parseCommandLine(args, 6);
            parseCommandLine(args, 8);
            parseCommandLine(args, 10);
            parseCommandLine(args, 12);
            parseCommandLine(args, 14);
        }

        //1. 获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //2. 获取数据源

        MySQLSource.Builder<String> sourceBuilder = MySQLSource.<String>builder()
                .hostname(hostname)
                .port(port)
                .username(username)
                .password(password)
                .databaseList(databaseList)
                .deserializer(new CustomDebeziumDeserializationSchema())
                .startupOptions(StartupOptions.initial());

        if(tableList.length > 0) {
            sourceBuilder.tableList(tableList);
        }

        DebeziumSourceFunction<String> mysqlSource = sourceBuilder.build();
        DataStreamSource<String> mysqlStreamSource = env.addSource(mysqlSource);
        mysqlStreamSource.print();

        mysqlStreamSource.addSink(KafkaUtil.getKafkaProducer(dbTopic, kafkaBrokers));

        //3. 启动flink任务
        try {
            env.execute("FlinkCdcApplication");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void parseCommandLine(String[] args, int index) {
        switch (args[index]) {
            case "--hostname":
                hostname = args[index+1];
                break;
            case "--port":
                port = Integer.parseInt(args[index+1]);
                break;
            case "--username":
                username = args[index+1];
                break;
            case "--password":
                password = args[index+1];
                break;
            case "--databaseList":
                String[] databaseSplit = args[index + 1].split(DELIMITER);
                if(databaseSplit.length == 0) {
                    System.err.println("未配置数据库列表，使用--databaseList指定");
                    System.exit(0);
                }
                databaseList = databaseSplit;
                break;

            case "--tableList":
                String[] tableSplit = args[index + 1].split(DELIMITER);
                if(tableSplit.length > 1 && !StringUtils.equals("all", tableSplit[0])) {
                    tableList = tableSplit;
                }
                break;
            case "--dbTopic":
                dbTopic = args[index+1];
                break;
            case "--kafkaBrokers":
                dbTopic = args[index+1];
                break;
        }
    }
}
