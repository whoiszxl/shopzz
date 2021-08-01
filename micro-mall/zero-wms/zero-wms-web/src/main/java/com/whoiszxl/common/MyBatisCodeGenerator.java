package com.whoiszxl.common;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * mybatis plus 代码生成器
 *
 * @author whoiszxl
 * @date 2021/7/12
 */
public class MyBatisCodeGenerator {

    //代码生成步骤一：配置连接的数据库、账号和密码
    public static final String DB_NAME = "zero-wms";
    public static final String URL = "jdbc:mysql://rm-bp1g8o86o5tdyze14xo.mysql.rds.aliyuncs.com/" + DB_NAME + "?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false";
    public static final String USERNAME = "mall";
    public static final String PASSWORD = "mall1020!!";

    //代码生成步骤二：配置表前缀，生成时自动省略表前缀
    public static final String tablePrefix = "wms_";

    //代码生成步骤三： 配置需要生成代码的表名
    public static final String[] tableNames = new String[]{
            "wms_fare_template"
    };

    //代码生成步骤四：直接运行就能生成了
    public static void main(String[] args) {
        //1. 创建代码生成器
        AutoGenerator generator = new AutoGenerator();

        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("whoiszxl");
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖
        //去除Service生成的 I前缀
        gc.setServiceName("%sService");
        gc.setIdType(IdType.ID_WORKER); //主键策略
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        generator.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(URL);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(USERNAME);
        dsc.setPassword(PASSWORD);
        dsc.setDbType(DbType.MYSQL);
        generator.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName("wms"); //模块名
        pc.setParent("com.whoiszxl");
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        generator.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setInclude(tableNames);
        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(tablePrefix); //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        generator.setStrategy(strategy);

        // 6、执行
        generator.execute();
    }
}
