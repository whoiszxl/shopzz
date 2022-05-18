package com.whoiszxl.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Property;

/**
 * mybatis代码生成器
 *
 * @author whoiszxl
 * @date 2021/12/7
 */
public class MyBatisFastAutoGenerator {

    //需要配置
    private static final String AUTHOR = "whoiszxl";
    private static final String PACKAGE_NAME = "com.whoiszxl";
    private static final String[] DB_TABLES = new String[]{
            "spms_seckill","spms_seckill_item","spms_seckill_order"
    };
    private static final Boolean ENABLE_SWAGGER = true;

    public static void main(String[] args) {
        // 1.数据源配置
        DataSourceConfig.Builder dataSourceConfigBuilder = new DataSourceConfig.Builder(
                "jdbc:mysql://aliyun.whoiszxl.com/shopzz?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false",
                "shopzz",
                "ECmzyDLN7bRSKtab");

        // 2.全局配置
        GlobalConfig.Builder globalConfigBuilder = new GlobalConfig.Builder();
        String projectPath = System.getProperty("user.dir");
        globalConfigBuilder.outputDir(projectPath + "/src/main/java");
        globalConfigBuilder.author(AUTHOR);
        globalConfigBuilder.disableOpenDir();
        if (ENABLE_SWAGGER){
            globalConfigBuilder.enableSwagger();
        }

        // 3.包配置
        PackageConfig.Builder packageConfigBuilder = new PackageConfig.Builder();
        packageConfigBuilder.parent(PACKAGE_NAME);

        // 4.策略配置
        StrategyConfig.Builder strategyConfigBuilder = new StrategyConfig.Builder();

        //过滤表前缀
        strategyConfigBuilder.addTablePrefix("sys_", "admin_", "oms_", "ums_", "vms_", "search_", "fms_", "rms_", "wms_", "pms_", "spms_");
        // 设置需要映射的表名
        strategyConfigBuilder.addInclude(DB_TABLES);
        // 下划线转驼峰
        strategyConfigBuilder.entityBuilder().naming(NamingStrategy.underline_to_camel);
        strategyConfigBuilder.entityBuilder().columnNaming(NamingStrategy.underline_to_camel);
        // entity的Lombok
        strategyConfigBuilder.entityBuilder().enableLombok();
        // 逻辑删除
        strategyConfigBuilder.entityBuilder().logicDeleteColumnName("is_deleted");
        strategyConfigBuilder.entityBuilder().logicDeletePropertyName("isDeleted");
        // 自动填充
        // 创建时间
        IFill createdAt = new Property("created_at", FieldFill.INSERT);
        // 更新时间
        IFill updatedAt = new Property("updated_at", FieldFill.INSERT_UPDATE);
        strategyConfigBuilder.entityBuilder().addTableFills(createdAt, updatedAt);
        // 乐观锁
        strategyConfigBuilder.entityBuilder().versionColumnName("version");
        strategyConfigBuilder.entityBuilder().versionPropertyName("version");
        // 使用RestController
        strategyConfigBuilder.controllerBuilder().enableRestStyle();
        // 将请求地址转换为驼峰命名，如 http://localhost:8080/hello_id_2
        strategyConfigBuilder.controllerBuilder().enableHyphenStyle();




        // 创建代码生成器对象，加载配置   对应1.2.3.4步
        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfigBuilder.build());
        autoGenerator.global(globalConfigBuilder.build());
        autoGenerator.packageInfo(packageConfigBuilder.build());
        autoGenerator.strategy(strategyConfigBuilder.build());

        // 执行
        autoGenerator.execute();
    }
}
