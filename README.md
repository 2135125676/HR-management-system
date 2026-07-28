# 人力资源管理系统

## 项目简介

人力资源管理系统是一个基于 Spring Boot 3 框架开发的人力资源内容管理系统，主要用于管理人力资源的活动资讯、文章发布、评论互动、轮播图展示等功能。系统采用前后端分离架构，后端提供 RESTful API 接口，支持 JWT 令牌认证、文件上传至七牛云、AI 智能对话等特性。

## 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 17 | 开发语言 |
| Spring Boot | 3.0.2 | 核心框架 |
| MyBatis Plus | 3.5.15 | ORM 框架 |
| MySQL | 8.0 | 数据库 |
| Knife4j | 4.4.0 | API 文档 |
| Hutool | 5.8.38 | 工具类库 |
| JWT | - | 令牌认证 |
| Qiniu SDK | 7.19.0 | 七牛云文件存储 |
| Bouncy Castle | 1.60 | SM4 加密 |
| Lombok | - | 简化代码 |

## 功能模块

### 1. 用户管理模块
- 用户注册与登录
- 用户信息管理
- 角色权限控制（管理员/普通用户）
- 账户状态管理

### 2. 资讯/文章模块
- 文章发布与管理
- 文章分类管理
- 文章审核状态管理
- 文章阅读统计
- 文章点赞功能

### 3. 栏目分类模块
- 多级栏目结构支持
- 栏目排序管理
- 栏目描述管理

### 4. 评论互动模块
- 文章评论功能
- 评论回复功能
- 评论审核管理
- 评论嵌套展示

### 5. 轮播图模块
- 轮播图管理
- 轮播图启用/禁用
- 轮播图排序

### 6. 系统配置模块
- 系统名称配置
- 系统图标配置
- 系统状态管理

### 7. 日志记录模块
- 操作日志自动记录
- 登录/登出日志
- 请求方式与路径记录

### 8. 文件上传模块
- 支持文件上传至七牛云
- 图片格式支持
- 文件大小限制（50MB）

### 9. AI 对话模块
- 集成 AI 智能对话
- 支持消息发送与响应

### 10. 权限认证模块
- JWT 令牌认证
- 登录状态拦截器
- 角色权限注解控制

## 项目结构

```
cms/
├── src/main/java/com/group2/cms/
│   ├── annotation/          # 自定义注解
│   │   ├── Log.java         # 日志记录注解
│   │   └── Role.java        # 角色权限注解
│   ├── aop/                 # 切面编程
│   │   ├── LogAspect.java   # 日志切面
│   │   └── RoleAspect.java  # 角色权限切面
│   ├── config/              # 配置类
│   │   ├── CryptoConfig.java    # 加密配置
│   │   ├── JWTProperties.java   # JWT配置
│   │   ├── MybatisPlusConfig.java # MyBatis Plus配置
│   │   ├── QiniuOssProperties.java # 七牛云配置
│   │   ├── SwaggerConfig.java   # Swagger配置
│   │   └── WebConfig.java       # Web配置
│   ├── constant/            # 常量类
│   ├── entity/              # 实体类
│   ├── exception/           # 异常处理
│   ├── generator/           # 代码生成器
│   ├── mapper/              # 数据访问层
│   ├── service/             # 业务逻辑层
│   │   ├── dto/             # 数据传输对象
│   │   ├── impl/            # 业务实现
│   │   └── I*Service.java   # 业务接口
│   ├── util/                # 工具类
│   ├── web/                 # Web层
│   │   ├── controller/      # 控制器
│   │   ├── interceptor/     # 拦截器
│   │   └── vo/              # 视图对象
│   └── CmsApplication.java  # 启动类
├── src/main/resources/
│   ├── mapper/              # MyBatis XML映射
│   ├── application.yml      # 应用配置
│   └── cms.sql              # 数据库脚本
└── pom.xml                  # Maven配置
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+

### 1. 克隆项目

```bash
git clone <仓库地址>
cd cms
```

### 2. 数据库配置

执行数据库脚本 `src/main/resources/cms.sql` 创建数据库表。

### 3. 修改配置文件

编辑 `src/main/resources/application.yml`：

```yaml
server:
  port: 8888

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/cms?serverTimezone=GMT%2B8
    username: <你的数据库用户名>
    password: <你的数据库密码>

jwt:
  key: <你的JWT密钥>
  expTime: 200

app:
  crypto:
    sm4-key: <你的SM4加密密钥>

qiniu:
  oss:
    access-key: <你的七牛云AccessKey>
    secret-key: <你的七牛云SecretKey>
    bucket: <你的七牛云存储桶名称>
    domain: <你的七牛云域名>
```

### 4. 构建项目

```bash
mvn clean package
```

### 5. 运行项目

```bash
mvn spring-boot:run
```

或者运行打包后的 jar：

```bash
java -jar target/cms-0.0.1-SNAPSHOT.jar
```

## API 文档

启动项目后访问 Knife4j API 文档：

```
http://localhost:8888/doc.html
```

### 主要接口

| 模块 | 接口路径 | 方法 | 说明 |
|------|---------|------|------|
| 登录 | /login | POST | 用户登录 |
| 登录 | /logout | POST | 用户登出 |
| 资讯 | /auth/article/page | GET | 分页查询资讯 |
| 资讯 | /auth/article | GET | 根据ID查询资讯 |
| 资讯 | /auth/article | POST | 新增或更新资讯 |
| 资讯 | /auth/article | DELETE | 删除资讯 |
| 资讯 | /auth/article/status | PUT | 修改资讯状态 |
| 栏目 | /auth/category | GET | 查询栏目列表 |
| 栏目 | /auth/category | POST | 新增栏目 |
| 栏目 | /auth/category | PUT | 更新栏目 |
| 栏目 | /auth/category | DELETE | 删除栏目 |
| 评论 | /auth/comment | GET | 查询评论列表 |
| 评论 | /auth/comment | POST | 新增评论 |
| 评论 | /auth/comment | DELETE | 删除评论 |
| 轮播图 | /auth/carousel | GET | 查询轮播图列表 |
| 轮播图 | /auth/carousel | POST | 新增轮播图 |
| 轮播图 | /auth/carousel | PUT | 更新轮播图 |
| 轮播图 | /auth/carousel | DELETE | 删除轮播图 |
| 用户 | /auth/user | GET | 查询用户列表 |
| 用户 | /auth/user | POST | 新增用户 |
| 用户 | /auth/user | PUT | 更新用户 |
| 用户 | /auth/user | DELETE | 删除用户 |
| 配置 | /auth/config | GET | 查询配置列表 |
| 配置 | /auth/config | POST | 新增配置 |
| 配置 | /auth/config | PUT | 更新配置 |
| 配置 | /auth/config | DELETE | 删除配置 |
| 文件 | /file/upload | POST | 文件上传 |
| AI | /auth/ai | POST | AI对话 |

## 数据库表结构

| 表名 | 说明 |
|------|------|
| base_user | 用户表 |
| base_config | 系统配置表 |
| base_log | 操作日志表 |
| cms_article | 资讯文章表 |
| cms_category | 栏目分类表 |
| cms_comment | 评论表 |
| cms_carousel | 轮播图表 |

## 团队成员分工

### 组长：罗健元
1. **基础文件**：`application.yml`、`pom.xml`
2. **功能模块**：资讯模块

### 组员：刘镇畅
1. **基础文件**：工具类
2. **功能模块**：栏目模块

### 组员：车荣健
1. **基础文件**：异常类
2. **功能模块**：评论模块

### 组员：唐静莹
1. **基础文件**：配置类、README.md
2. **功能模块**：轮播图模块、配置模块

### 组员：漆明珠
1. **基础文件**：常量类
2. **功能模块**：用户模块

## 许可证

本项目仅供学习和教学使用。
