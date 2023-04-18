package com.chengxi.fitday;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;


//代码生成器，可能会覆盖之前的代码，请谨慎使用！！！！！！！！


public class Generator {
    public static void main(String[] args) {
        AutoGenerator autoGenerator = new AutoGenerator();
        //    告诉它怎么生成，在哪生成
        //datasource数据源配置
        DataSourceConfig dataSource = new DataSourceConfig();
        dataSource.setDriverName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/fitday?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("3925014");
        autoGenerator.setDataSource(dataSource);
        //会在D盘生成一个com文件，但是这个位置是不对的，需要我们再进一步配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir")+"/src/main/java");
        //globalConfig.setOutputDir(System.getProperty("user.dir")+"\\src\\main\\java");
        //      \\src\\main\\java
        //设置完之后是否打开资源管理器   NO
        globalConfig.setOpen(false);
        //设置作者
        globalConfig.setAuthor("成溪科技公司开发");
        //设置是否覆盖原始生成的文件
        globalConfig.setFileOverride(true);
        autoGenerator.setGlobalConfig(globalConfig);

        PackageConfig packageConfig  =new PackageConfig();
        //设置生成的包名，与代码所在位置不冲突，二者叠加组成完整路径
        packageConfig.setParent("com.chengxi.fitday");
        autoGenerator.setPackageInfo(packageConfig);

        StrategyConfig strategyConfig = new StrategyConfig();


        //在使用之前，设置数据库生成的表，一定不要填目前已经生成过的，否则会覆盖！！！
        //修改下面这行代码开始使用：
        strategyConfig.setInclude(".....");
        //生成后第一时间把里面的字改回来！！！
        //生成后去改实体类为驼峰，去加@Mapper



        //是否启用Rest风格
        strategyConfig.setRestControllerStyle(true);
        //设置逻辑删除字段名
        strategyConfig.setLogicDeleteFieldName("is_deleted");
        //设置是否启用Lombok
        strategyConfig.setEntityLombokModel(true);
        autoGenerator.setStrategy(strategyConfig);

        //3. 执行生成操作
        autoGenerator.execute();
    }
}
