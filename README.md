yxhshop |  基于微服务的小程序商城系统
---
yxhshop是基于Spring Cloud(Greenwich)开发的小程序商城系统，提供整套公共微服务服务模块，包含用户中心、商品中心、订单中心、存储中心四大基础服务模块，支持服务治理、监控、日志收集和追踪等功能。

## 请注意

管理平台功能还没有实现，很多功能未完善，没太多时间维护。感兴趣的Fork一份自己完善。

## 组织结构

```
yxhshop
├── yxhshop-common -- 框架公共模块
├── yxhshop-eureka-server -- eureka注册中心[端口:8761]
├── yxhshop-config-server -- 配置中心
├── yxhshop-api-gateway -- api网关[端口:8020]
├── yxhshop-user-api -- 用户中心api
├── yxhshop-user -- 用户中心基础服务
├── yxhshop-goods-api -- 商品中心api
├── yxhshop-goods -- 商品中心基础服务
├── yxhshop-order-api -- 订单中心api
├── yxhshop-order -- 订单中心基础服务
├── yxhshop-storage-api -- 对象存储服务api
├── yxhshop-storage -- 对象存储服务
├── yxhshop-wechat-ui -- 微信小程序页面
├── yxhshop-admin-ui -- 管理平台页面
```

> 模块划分


### 后端技术

技术 | 名称 | 版本 | 官网
----|------|----|----
Spring Boot | 应用框架 | 2.1.2.RELEASE | [https://projects.spring.io/spring-boot/](https://projects.spring.io/spring-boot/)
spring-cloud-netflix | 微服务框架 | Greenwich.RELEASE | [https://projects.spring.io/spring-cloud/](https://projects.spring.io/spring-boot/)
MyBatis | ORM框架 | 3.2.1 |  [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)
Mapper | MyBatis 通用 Mapper4 | 4.0.0 |  [https://gitee.com/free/Mapper](https://gitee.com/free/Mapper)
MyBatis Generator | 代码生成 | 1.3.5 |  [http://www.mybatis.org/generator/index.html](http://www.mybatis.org/generator/index.html)
Swagger2 | 在线Api文档 | 2.9.2 |  [https://swagger.io/](https://swagger.io/)
Thymeleaf | 模板引擎 | 3.0.9.RELEASE |  [https://www.thymeleaf.org/](https://www.thymeleaf.org/)
Logback | 日志组件 | 1.1.3 |  [https://logback.qos.ch](https://logback.qos.ch/)
ELK | 日志收集 6.4.0版本的elaticSearch、kibana、lostash7.6.2  可选组件
Druid | 数据库连接池 | 0.2.23 |  [https://github.com/alibaba/druid](https://github.com/alibaba/druid)
Hibernate Validator | 后端校验框架 | 5.4.2.Final | [http://hibernate.org/validator/](http://hibernate.org/validator/)
RocketMQ | 消息中间件 | 4.6.0 | [http://www.rabbitmq.com/](http://www.rabbitmq.com/)
Redis | 缓存 | 5.0.3 | [https://redis.io/](https://redis.io/)
Zipkin | 链路追踪 | 2.12.0 | [https://zipkin.io/](https://zipkin.io/)

### 前端技术

技术 | 名称 | 版本 |  官网
----|------|----|----
vue | 前端MVC框架 | 10.0 以上|  [https://reactjs.org/](https://reactjs.org/)
ELEMENT-UI / 前端组件 / 2.12.0+ / [https://element.eleme.cn/#/zh-CN]

### 软件需求

- JDK1.8+
- MySQL5.6+
- RocketMQ 4.6.0+
- Maven3.0+
- ZipKinServer 3.7.0+

## 功能

### 商城功能

- 首页
- 专题列表、专题详情
- 分类列表、分类详情
- 品牌列表、品牌详情
- 新品首发、人气推荐
- 搜索
- 商品详情、商品评价、商品分享
- 购物车
- 下单
- 订单列表、订单详情
- 地址、收藏、足迹、意见反馈
- 客服

### 管理平台功能(未完成)

-  [ ]  会员管理
-  [ ]  商城管理
-  [ ]  商品管理
-  [ ]  订单管理
-  [ ]  推广管理
-  [ ] 系统管理

## 当前进度与计划
集成Turbine 监控数据
管理平台端的开发

### 在线演示

。。。

### 预览图



## 安装教程

### 本地部署

1. 通过git下载源码
2. 创建数据库yxhshop，数据库编码为UTF-8
3. 执行docs/sql/yxhshop.sql文件，初始化数据
4. 修改配置中心(yxhshop-config-server)的database.properties和common.properties文件，更新MySQL账号和密码，更新RocketMQ配置，更新zipkinServer配置
5. 运行Maven命令mvn install(注意：安装yxhshop-admin模块因为会运行npm install和npm build命令时间会比较长，当然也可以手动在yxhshop-admin模块执行npm命令)
6. 安装yxhshop-admin-ui模块，运行mvn install和mvn build命令，运行命令前需要安装nodeJs
7. 运行yxhshop-eureka-server、yxhshop-config-server、yxhshop-api-gateway这几个基础服务
8. 运行yxhshop-user、yxhshop-goods、yxhshop-order这几个api服务
9. http://localhost:8089/index.html访问后台管理，http://localhost:8089/swagger-ui.html访问Swagger页面
10. 打开微信开发者工具，导入yxhshop-wechat-ui模块,点击编译即可

使用脚本启动: 
1. 执行命令 `mvn clean package -P env-prd` 生成target文件夹包含项目所有jar包和执行脚本
2. 设置权限 `chmod +x -R target/`
4. 切换到target目录 `cd target/`
3. 启动: `startup-all.sh` 停止: `shutdown-all.sh`

### 生产部署

最低部署要求  1C2G x3





