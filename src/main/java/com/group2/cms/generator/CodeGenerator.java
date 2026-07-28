package com.group2.cms.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * 基于MybatisPlus代码生成器的构建工具
 *
 * @author luojy
 * */
public class CodeGenerator {
    // ctrl d
    public static String url = "jdbc:mysql://localhost:3306/cms?serverTimezone=GMT%2B8";
    public static String username = "root";
    public static String password = "root";
    public static String author = "luojy";
    public static String basePackage = "com.group2.cms";
    public static String mapperXMLPath = System.getProperty("user.dir") + "\\src\\main\\resources\\mapper";

    public static void main(String[] args) {
// 使用 FastAutoGenerator 快速配置代码生成器
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .disableOpenDir() //生成代码，默认不打开路径
                            //.enableSwagger() // 开启Swagger
                            .outputDir("src/main/java"); // 输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(basePackage) // 设置父包名
                            .entity("entity") // 设置实体类包名
                            .mapper("mapper") // 设置 Mapper 接口包名
                            .service("service") // 设置 Service 接口包名
                            .serviceImpl("service.impl") // 设置 Service 实现类包名
                            .controller("web.controller") // 设置controller类包，
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperXMLPath)); // 设置路径配置信息
                })
                // 策略配置信息：根据总体设计 详细设计 数据库设计 编码 数据库创建 ，生成对应java代码
                .strategyConfig(builder -> {
                    builder.addInclude("^cms_.*") // 设置需要生成的表名前缀
                            .addTablePrefix("cms_") // 设置过滤表的前缀
                            .addFieldPrefix("log_","article_", "category_", "comment_", "carousel_") // 设置过滤字段前缀
                            .entityBuilder()
                            .enableLombok() // 启用 Lombok
                            .enableTableFieldAnnotation() // 启用字段注解
                            .controllerBuilder()
                            .enableRestStyle(); // 启用 REST 风格
                    // 可以设置web service dao 层的具体方法信息
                    builder.entityBuilder()
                            .enableLombok() // 开启lomlok
                            .enableFileOverride(); // 允许文件重写
                    builder.controllerBuilder()
                            .enableRestStyle();
                    builder.serviceBuilder();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用 Freemarker 模板引擎
                .execute(); // 执行生成
    }
}
