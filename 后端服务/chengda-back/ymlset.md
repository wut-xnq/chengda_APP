### moudles

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="ProjectModuleManager">
    <modules>
      <module fileurl="file://$PROJECT_DIR$/cm-auth/cm-auth.iml" filepath="$PROJECT_DIR$/cm-auth/cm-auth.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-common/cm-common-core/cm-common-core.iml" filepath="$PROJECT_DIR$/cm-common/cm-common-core/cm-common-core.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-common/cm-common-datasource/cm-common-datasource.iml" filepath="$PROJECT_DIR$/cm-common/cm-common-datasource/cm-common-datasource.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-common/cm-common-feign/cm-common-feign.iml" filepath="$PROJECT_DIR$/cm-common/cm-common-feign/cm-common-feign.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-common/cm-common-log/cm-common-log.iml" filepath="$PROJECT_DIR$/cm-common/cm-common-log/cm-common-log.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-common/cm-common-mybatis/cm-common-mybatis.iml" filepath="$PROJECT_DIR$/cm-common/cm-common-mybatis/cm-common-mybatis.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-common/cm-common-oss/cm-common-oss.iml" filepath="$PROJECT_DIR$/cm-common/cm-common-oss/cm-common-oss.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-common/cm-common-seata/cm-common-seata.iml" filepath="$PROJECT_DIR$/cm-common/cm-common-seata/cm-common-seata.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-common/cm-common-security/cm-common-security.iml" filepath="$PROJECT_DIR$/cm-common/cm-common-security/cm-common-security.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-common/cm-common-swagger/cm-common-swagger.iml" filepath="$PROJECT_DIR$/cm-common/cm-common-swagger/cm-common-swagger.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-common/cm-common-xss/cm-common-xss.iml" filepath="$PROJECT_DIR$/cm-common/cm-common-xss/cm-common-xss.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-consu/cm-consu-api/cm-consu-api.iml" filepath="$PROJECT_DIR$/cm-consu/cm-consu-api/cm-consu-api.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-consu/cm-consu-biz/cm-consu-biz.iml" filepath="$PROJECT_DIR$/cm-consu/cm-consu-biz/cm-consu-biz.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-gateway/cm-gateway.iml" filepath="$PROJECT_DIR$/cm-gateway/cm-gateway.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-merch/cm-merch-api/cm-merch-api.iml" filepath="$PROJECT_DIR$/cm-merch/cm-merch-api/cm-merch-api.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-merch/cm-merch-biz/cm-merch-biz.iml" filepath="$PROJECT_DIR$/cm-merch/cm-merch-biz/cm-merch-biz.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-register/cm-register.iml" filepath="$PROJECT_DIR$/cm-register/cm-register.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-upms/cm-upms-api/cm-upms-api.iml" filepath="$PROJECT_DIR$/cm-upms/cm-upms-api/cm-upms-api.iml" />
      <module fileurl="file://$PROJECT_DIR$/cm-upms/cm-upms-biz/cm-upms-biz.iml" filepath="$PROJECT_DIR$/cm-upms/cm-upms-biz/cm-upms-biz.iml" />
    </modules>
  </component>
</project>
com.jiaolong.cm.nacos.bootstrap
```



### application.yml

```yml
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



### auth

```yml
# 数据源
spring:
  freemarker:
    allow-request-override: false
    allow-session-override: false
    cache: true
    charset: UTF-8
    check-template-location: true
    content-type: text/html
    enabled: true
    expose-request-attributes: false
    expose-session-attributes: false
    expose-spring-macro-helpers: true
    prefer-file-system-access: true
    suffix: .ftl
    template-loader-path: classpath:/templates/


security:
  encode-key: 'thanks,pig4cloud'
  ignore-clients: 
    - test
    - custom
    - merch
    - pig
```

### umps

```yml
# 数据源
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: 
    password: root
    url: jdbc:mysql://cm-mysql:3306/cm?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true

# 文件上传相关 支持阿里云、华为云、腾讯、minio
file:
  bucketName: s3demo 
  local:
    enable: true
    base-path: /Users/lengleng/Downloads/img
```



### gateway

```yaml
spring:
  cloud:
    gateway:
      locator:
        enabled: true
      routes:
        # 认证中心
        - id: auth
          uri: lb://auth
          predicates:
            - Path=/auth/**
        #UPMS 模块
        - id: upms
          uri: lb://upms
          predicates:
            - Path=/admin/**
          filters:
            # 限流配置
            - name: RequestRateLimiter
              args:
                key-resolver: '#{@remoteAddrKeyResolver}'
                redis-rate-limiter.replenishRate: 100
                redis-rate-limiter.burstCapacity: 200
        #CONSU 模块
        - id: consu
          uri: lb://consu
          predicates:
            - Path=/consu/**
          filters:
            # 限流配置
            - name: RequestRateLimiter
              args:
                key-resolver: '#{@remoteAddrKeyResolver}'
                redis-rate-limiter.replenishRate: 100
                redis-rate-limiter.burstCapacity: 200
        #CONSU 模块
        - id: merch
          uri: lb://merch
          predicates:
            - Path=/merch/**
          filters:
            # 限流配置
            - name: RequestRateLimiter
              args:
                key-resolver: '#{@remoteAddrKeyResolver}'
                redis-rate-limiter.replenishRate: 100
                redis-rate-limiter.burstCapacity: 200
        # 代码生成模块
        - id: cm-codegen
          uri: lb://cm-codegen
          predicates:
            - Path=/gen/**
        # 代码生成模块
        - id: cm-quartz
          uri: lb://cm-quartz
          predicates:
            - Path=/job/**
        # 固定路由转发配置 无修改
        - id: openapi
          uri: lb://cm-gateway
          predicates:
            - Path=/v3/api-docs/**
          filters:
            - RewritePath=/v3/api-docs/(?<path>.*), /$\{path}/$\{path}/v3/api-docs
```

### consu.yml

```yaml
# 数据源
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: 
    password: root
    url: jdbc:mysql://cm-mysql:3306/cm?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true

# 文件上传相关 支持阿里云、华为云、腾讯、minio
file:
  bucketName: s3demo 
  local:
    enable: true
    base-path: /Users/lengleng/Downloads/img
```

### merch

```yaml
# 数据源
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: 
    password: root
    url: jdbc:mysql://cm-mysql:3306/cm?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true&allowPublicKeyRetrieval=true

# 文件上传相关 支持阿里云、华为云、腾讯、minio
file:
  bucketName: s3demo 
  local:
    enable: true
    base-path: /Users/lengleng/Downloads/img
```

