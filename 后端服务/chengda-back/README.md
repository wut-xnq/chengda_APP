<p align="center">
 <img src="https://img.shields.io/badge/Pig-3.8-success.svg" alt="Build Status">
 <img src="https://img.shields.io/badge/Spring%20Cloud-2023-blue.svg" alt="Coverage Status">
 <img src="https://img.shields.io/badge/Spring%20Boot-3.3-blue.svg" alt="Downloads">
 <img src="https://img.shields.io/badge/Vue-3.4-blue.svg" alt="Downloads">
 <img src="https://img.shields.io/github/license/pig-mesh/pig"/>
</p>

### 系统说明

- 本系统是基于pig架构的jdk17分支搭建，原地址：https://gitee.com/log4j/pig/tree/jdk17
- 项目技术栈：基于 Spring Cloud 、Spring Boot、 OAuth2 的 RBAC **企业快速开发平台**， 同时支持微服务架构和单体架构
- 提供对 Spring Authorization Server 生产级实践，支持多种安全授权模式
- 提供对常见容器化方案支持 Kubernetes、Rancher2 、Kubesphere、EDAS、SAE 支持

### 技术栈说明

- java17/21 + springboot 3.3 + springcloud 2023

### 核心依赖

| 依赖                          | 版本         |
|-----------------------------|------------|
| Spring Boot                 | 3.3.1      |
| Spring Cloud                | 2023.0.3   |
| Spring Cloud Alibaba        | 2023.0.1.2 |
| Spring Authorization Server | 1.3.1      |
| Mybatis Plus                | 3.5.7      |

### 模块说明

```lua
cm
├── cm-boot -- 单体模式启动器[9999]
├── cm-auth -- 授权服务[3000]
└── cm-common -- 系统公共模块
     ├── cm-common-bom -- 全局依赖管理控制
     ├── cm-common-core -- 公共工具类核心包
     ├── cm-common-datasource -- 动态数据源包
     ├── cm-common-log -- 日志服务
     ├── cm-common-oss -- 文件上传工具类
     ├── cm-common-mybatis -- mybatis 扩展封装
     ├── cm-common-seata -- 分布式事务
     ├── cm-common-security -- 安全工具类
     ├── cm-common-swagger -- 接口文档
     ├── cm-common-feign -- feign 扩展封装
     └── cm-common-xss -- xss 安全封装
├── cm-gateway -- SpringCloud Gateway网关[9999]
└── cm-visual
     └── cm-codegen -- 图形化代码生成 [5002]
└── cm-upms -- 通用管理端用户权限管理模块
     ├── cm-upms-api -- 公共api模块
     └── cm-upms-biz -- 业务处理模块[4000]
└── cm-consu -- 通用消费端用户权限管理模块
     ├── cm-consu-api -- 公共api模块
     └── cm-consu-biz -- 业务处理模块[7100]
└── cm-merch -- 通用商户端用户权限管理模块
     ├── cm-merch-api -- 公共api模块
     └── cm-merch-biz -- 业务处理模块[7200]
```

### 本地开发 运行

pig 官方提供了详细的[部署文档 wiki.pig4cloud.com](https://www.yuque.com/pig4cloud/pig/vsdox9)，包括开发环境安装、服务端代码运行、前端代码运行等。

请务必**完全按照**文档部署运行章节 进行操作，减少踩坑弯路！！

### 开源协议

pig 开源软件遵循 [Apache 2.0 协议](https://www.apache.org/licenses/LICENSE-2.0.html)。
允许商业使用，但**务必保留类作者、Copyright**信息。

![](https://minio.pigx.top/oss/1655474288.jpg)

### 前端问题记录

```shell
## 问题
> cm-ui@3.8.0 dev
> vite --force

'vite' 不是内部或外部命令，也不是可运行的程序
或批处理文件。

##解决
#1.第一步，设置镜像（原有的淘宝镜像安装失败）
npm config set registry https://registry.npmjs.org/
#2.第二步，按照vite
npm install -g vite
# 验证是否安装命令
npm list -g vite
#3.第三步，配置vite到系统变量（包含bin目录）

```

### 后端启动

```shell
#启动nacos
.\startup.cmd -m standalone
#测试账号密码：admin/123456

```

·

```shell
spring:
  cache:
    type: redis # 缓存类型 Redis
  data:
    redis:
      host: cm-redis  # Redis地址
      password: # Redis密码，没有留空
      port: 6379  # Redis端口
  # 数据库相关配置
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: root
    url: jdbc:mysql://cm-mysql:3306/cm?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&nullCatalogMeansCurrent=true

# 本地文件系统
file:
  local:
    enable: true
    base-path: /Users/lengleng/Downloads/img

## 登录配置

security:
  micro: true
  encodeKey: thanks,pig4cloud
  oauth2:
    ignore:
      urls:
          - /webjars/**
        - /v3/api-docs/**
        - /doc.html
        - /swagger-ui.html
        - /swagger-ui/**
        - /swagger-resources
        - /token/check_token
        - /error
        - /token/**
        - /actuator/**
        - /auth/code/**
  ignore-clients:
    - test

# 配置文件加密根密码
jasypt:
  encryptor:
    password: pig  # 加密根密码
    algorithm: PBEWithMD5AndDES  # 加密算法
    iv-generator-classname: org.jasypt.iv.NoIvGenerator # 无向量生成器

# swagger 配置
swagger:
  token-url: ${swagger.gateway}/admin/oauth2/token

```





```json
# 配置文件加密根密码
jasypt:
  encryptor:
    password: pig
    algorithm: PBEWithMD5AndDES
    iv-generator-classname: org.jasypt.iv.NoIvGenerator
    
# Spring 相关
spring:
  cache:
    type: redis
  data:
    redis:
      host: cm-redis
  cloud:
    sentinel:
      eager: true
      transport:
        dashboard: cm-sentinel:5003
    openfeign:
      sentinel:
        enabled: true
      okhttp:
        enabled: true
      httpclient:
        enabled: false
      compression:
        request:
          enabled: true
        response:
          enabled: true

# 暴露监控端点
management:
  endpoints:
    web:
      exposure:
        include: "*"  
  endpoint:
    health:
      show-details: ALWAYS

# mybaits-plus配置
mybatis-plus:
  mapper-locations: classpath:/mapper/*Mapper.xml
  global-config:
    banner: false
    db-config:
      id-type: auto
      table-underline: true
      logic-delete-value: 1
      logic-not-delete-value: 0
  type-handlers-package: com.jiaolong.cm.common.mybatis.handler
  configuration:
    map-underscore-to-camel-case: true
    shrink-whitespaces-in-sql: true

# swagger 配置
swagger:
  enabled: true
  title: Pig Swagger API
  gateway: http://${GATEWAY_HOST:cm-gateway}:${GATEWAY-PORT:9999}
  token-url: ${swagger.gateway}/auth/oauth2/token
  scope: server
  services:
    cm-upms-biz: admin
    cm-consu-biz: consu
    cm-merch-biz: merch
    cm-codegen: gen
```

