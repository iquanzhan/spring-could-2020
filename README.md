# spring-could-2020

spring cloud学习笔记，基于spring boot 2.新版本，集成Spring Cloud、Spring Cloud Alibaba相关微服务组件。

# 一、版本选择

## 1.1 Spring Boot版本选择

git源码地址：

http://github.com/spring-projects/spring-boot/releases/

2.0版本说明：

https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Release-Notes

> 官方强烈建议我们升级到2.x版本

查看spring boot最新版本:

https://spring.io/projects/spring-boot

## 1.2 Spring Cloud版本选择

官网地址：

https://spring.io/projects/spring-cloud

可通过官网查看最新版本。

## 1.3 spring boot和spring cloud依赖关系

官网地址：

https://spring.io/projects/spring-cloud#overview

可以找到spring boot对应的 spring cloud版本推荐。

![img](assets/00831rSTly1gd8w5g9o4jj30n207h0t0.jpg)

也可访问：https://start.spring.io/actuator/info

获取更详细的依赖信息。

# 二、本项目使用版本

Spring Cloud：Hoxton.SR1

Spring Boot：2.2.2.RELEASE

Spring Alibaba Cloud：alibaba 2.1.0.RELEASE

java：1.8

Maven：3.5及以上

# 三、总体使用组件

![img](assets/00831rSTly1gd8w583lzcj30la06w0vb.jpg)

# 四、创建父工程

1.新建普通maven工程

2.添加pom文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.chengxiaoxiao.springcloud</groupId>
    <artifactId>spring-cloud-parent</artifactId>
    <version>1.0-SNAPSHOT</version>

    <!--统一管理jar包和版本-->
    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <junit.version>4.12</junit.version>
        <log4j.version>1.2.17</log4j.version>
        <lombok.version>1.16.18</lombok.version>
        <mysql.version>8.0.18</mysql.version>
        <druid.verison>1.1.16</druid.verison>
        <mybatis.spring.boot.verison>1.3.0</mybatis.spring.boot.verison>
    </properties>
    <dependencyManagement>
        <dependencies>
            <!--spring boot 2.2.2-->
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.2.2.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud Hoxton.SR1-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud alibaba 2.1.0.RELEASE-->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.2.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!-- MySql -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
            </dependency>
            <!-- Druid -->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid-spring-boot-starter</artifactId>
                <version>${druid.verison}</version>
            </dependency>
            <!-- mybatis-springboot整合 -->
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>${mybatis.spring.boot.verison}</version>
            </dependency>
            <!--lombok-->
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
            </dependency>
            <!--junit-->
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>${junit.version}</version>
            </dependency>
            <!-- log4j -->
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>${log4j.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <addResources>true</addResources>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

dependencyManagement节点是管理依赖版本号的一种方式。可以使子模块无需，添加版本号。dependencyManagement只是声明，并不添加依赖。以此达到统一版本处理。

父工程创建完成了，要先执行mvn:install将父工程发布到仓库，方便子工程继承。

# 五、REST基础微服务

## 5.1 微服务提供者

1.建module

2.改POM文件

```pom
<dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>

    </dependencies>
```

3.yml文件修改

```yml
server:
  port: 8001

spring:
  application:
    name: cloud-payment-service

  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://192.168.4.188:3306/cloud2020?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true
    username: root
    password: 123456

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.chengxiaoxiao.cloud.entities #所有entity别名所在包
```

添加数据库：

```sql
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `serial` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

添加entities

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment implements Serializable {
    private Long id;
    private String serial;
}
```

添加CommonResult

```code
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    private Integer code;
    private String msg;
    private T data;
}
```

添加Dao层

```java
@Mapper
public interface PaymentDao {
    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
```

添加service

```java
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}

```

添加mapper.xml文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.chengxiaoxiao.cloud.dao.PaymentDao">
    <insert id="create" parameterType="Payment" useGeneratedKeys="true" keyProperty="id">
        insert into payment(serial) values(#{serial})
    </insert>


    <resultMap id="BaseResultMap" type="com.chengxiaoxiao.cloud.entities.Payment">
        <id column="id" property="id" jdbcType="BIGINT"/>
        <id column="serial" property="serial" jdbcType="VARCHAR"/>
    </resultMap>

    <select id="getPaymentById" parameterType="Long" resultMap="BaseResultMap">
        select * from payment where id=#{id}
    </select>
</mapper>
```



postman测试

![img](assets/00831rSTly1gd8w4jpk3pj30qo0bo3zq.jpg)

![img](assets/00831rSTly1gd8w4t07pqj30qu0cfabb.jpg)

## 5.2 配置热部署

1.添加依赖

```maven
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
```

2.添加plugin，放入父工程

```
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <addResources>true</addResources>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

3.开启自动编译

![image-20200328154621833](assets/image-20200328154621833.png)

help->find action ->registry

![image-20200328154729734](assets/image-20200328154729734.png)

## 5.3 微服务消费者

1.建立module

2.添加pom依赖

```pom
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
```

3.建yml

```java
server:
  port: 80
spring:
  application:
    name: cloud-consumer-order
```

4.添加启动类

```
@SpringBootApplication
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
```

5.写controller实现调用微服务提供者，实现对其远程调用

使用RestTemplate是一种简单便捷的访问restful的服务类，用它可以远程调用url。

官网地址：https://docs.spring.io/spring-framework/docs/5.2.2.RELEASE/javadoc-api/org/springframework/web/client/RestTemplate.html

添加config，注入进入工程。

```
@Configuration
public class ApplicationContextConfig {
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
```

> RestTemplate:主要包括getforObject/getForEntity。entity返回了更多的信息，如请求头之类的。forObject主要返回响应体

controller

```
@RestController
public class PaymentController {

    private final String PAYMENT_URL = "http://localhost:8001";
    @Resource
    private RestTemplate restTemplate;

    @PostMapping("/consumer/payment/create")
    public CommonResult create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/{id}")
    public CommonResult getPayment(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonResult.class);
    }
}
```

测试

![image-20200328161159601](assets/image-20200328161159601.png)

![image-20200328161237697](assets/image-20200328161237697.png)

# 六、工程重构

目前工程中entiyies在每个module中都需要重新建立一份，而这些信息，是通用的。完全可以提出来新建立一个module，让所有module依赖此公用module。

新建cloud-api-common module

添加依赖

```
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.1.0</version>
        </dependency>
    </dependencies>
```

拷贝entities文件。

mvn:install

其他项目添加依赖：

```xml
        <dependency>
            <groupId>com.chengxiaoxiao.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>${project.version}</version>
        </dependency>
```

# 七、Eureka服务注册与发现

## 7.1相关概念

1.什么是服务治理？

传统rpc远程调用中，服务与服务之间依赖和管理比较复杂，所以需要引入服务治理框架，管理他们之间的依赖关系，同时又实现了服务调用、负载均衡、容错等，实现了服务的注册与发现。

2.什么是服务注册与发现？

把服务注册到注册中去，消费者去注册中心上查询注册了哪些服务，服务有多少个实例，哪些是健康的，哪些是不可用，可以称之为服务发现

## 7.2单机版Eureka

### 1.服务端注册中心

1.1建立module cloud-eureka-server7001

1.2添加pom文件依赖

```maven
 <!--eureka-server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>

        <dependency>
            <groupId>com.chengxiaoxiao.springcloud</groupId>
            <artifactId>cloud-api-common</artifactId>
            <version>${project.version}</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
```

1.3yml文件

```xml
server:
  port: 7001

eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false #表示不向注册中心注册自己
    fetch-registry: false #false表示自己就是注册中心，我的职责就是维护服务实例,并不区检索服务
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ # 不搭建集群 单机 指向自己
```

1.4添加启动类

```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class,args);
    }
}
```

1.5测试访问：http://localhost:7001

![image-20200329143936618](assets/image-20200329143936618.png)

### 2.客户端集成

把8001集成euarke客户端

1.添加pom依赖

```
       <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```

2.改写yml

```java
eureka:
  client:
    register-with-eureka: true #表示向注册中心注册自己 默认为true
    fetch-registry: true #是否从EurekaServer抓取已有的注册信息，默认为true,单节点无所谓,集群必须设置为true才能配合ribbon使用负载均衡
    service-url:
      defaultZone: http://localhost:7001/eureka/
```

3.改写启动类

```java
@SpringBootApplication
@EnableEurekaClient
public class PaymentMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }
}
```

4.测试，先启动server 在启动客户端

![image-20200329151626758](assets/image-20200329151626758.png)

自我保护机制：默认情况下，当某个服务宕机后，euarke server不会立即在注册中心将其删除掉。而是当过一段时间后，到达某个阈值之后，才会将其移除。

## 7.3集群版Eureka

Erreka集群版，主要是server端的互相注册，互相守望。

下面新建一个Euarka server实现。

1.添加依赖

```
        <!--eureka-server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
```

2.添加yml

7001守望7002

```
server:
  port: 7001

eureka:
  instance:
    hostname: eureka7001.com
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://eureka7002.com:7002/eureka/
spring:
  application:
    name: eureka-server-7001
```

7002守望7001

```
server:
  port: 7002
eureka:
  instance:
    hostname: eureka7002.com
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/
spring:
  application:
    name: eureka-server-7002
```

3.添加主启动类

7002添加主启动类

```
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerMain7002 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaServerMain7002.class, args);
    }
}
```

### 客户端注册进集群

客户端yml配置修改

```
eureka:
  client:
    register-with-eureka: true #表示向注册中心注册自己 默认为true
    fetch-registry: true #是否从EurekaServer抓取已有的注册信息，默认为true,单节点无所谓,集群必须设置为true才能配合ribbon使用负载均衡
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/
```

测试：

![image-20200329155338013](assets/image-20200329155338013.png)

## 7.4负载均衡

消费者不能写死提供者IP。

```java
private final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";
```

直接写服务名。并在RestTemplate上开启负载均衡功能

```java
@Configuration
public class ApplicationContextConfig {
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
```

## 7.5补充信息

```
  instance:
    instance-id: payment8002
    prefer-ip-address: true
```

格式化名称、显示IP

## 7.6服务发现

对于注册Eureka里面的微服务可以通过服务发现来获得该服务的信息。

主启动类添加@EnableDiscoveryClient注解，开启服务发现。

```
    @Resource
    private DiscoveryClient discoveryClient;
        @GetMapping("/payment/discovery")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();

        for (String service : services) {
            log.info("**********" + service);
        }

        //一个微服务下的全部实例
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getServiceId() + instance.getHost() + ":" + instance.getPort() + "/" + instance.getUri());
        }
        return this.discoveryClient;
    }

```

## 7.7自我保护机制

简单理解：某个时刻一个微服务不可用了。Eureka不会立即清理，依旧会对该服务的信息做保存。

默认情况下，是开启自我保护机制的。

服务端设置：关闭自我保护机制：

```
eureka:
  instance:
    hostname: eureka7001.com
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://eureka7002.com:7002/eureka/
      server:
        enable-self-preservation: false # 关闭自我保护机制 保证不可用服务及时清除
        eviction-interval-timer-in-ms: 2000
```

客户端可以设置：

eureka.instance.lease-renewal-interval-in-seconds=30

*Eureka* 客户端向服务端发送心跳的时间间隔 *,* 单位为秒 *(* 默认是 *30* 秒 *)*

eureka.instance.lease-expiration-duration-in-seconds=90

*Eureka* 服务端在收到最后一次心跳后等待时间上限  *,* 单位为秒 *(* 默认是 *90* 秒 *),* 超时剔除服务 

**属于CAP的AP分支。**

# 八、Zookeeper替代Eureka

ZooKeeper是一个分布式协调工具，可以作为eureka的替代。使用zk作为服务注册中心。

暂不总结

# 九、Consul服务注册与发现

## 9.1简介

Consul是一个开源的分布式服务发现与配置系统。

主要有以下特性：

1.服务发现：提供对HTPP/DNS两种发现方式

2.健康检测：支持多种方式，HTTP、TCP、Docker、shell脚本定制化

3.提供KVC存储

4.支持多数据中心

5.可视化界面

## 9.2下载与使用

下载地址：https://www.consul.io/downloads.html

中文文档：https://www.springcloud.cc/spring-cloud-consul.html

### 启动

```
consul agent -dev
```

可通过http://localhost:8500访问。

## 9.3新建服务提供者

1.pom

```pom
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>
```

2.yml

```yml
server:
  # consul服务端口
  port: 8006
spring:
  application:
    name: cloud-provider-payment
  cloud:
    consul:
      # consul注册中心地址
      host: localhost
      port: 8500
      discovery:
        hostname: 127.0.0.1
        service-name: ${spring.application.name}
```

3.启动类：

```java
@SpringBootApplication
@EnableDiscoveryClient
public class PaymentMain8006 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8006.class, args);
    }
}
```

## 9.4新建服务提供者

1.pom

```
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-consul-discovery</artifactId>
        </dependency>
```

2.启动类

```yml
server:
  port: 80
spring:
  application:
    name: cloud-consumer-order
  cloud:
    consul:
      # consul注册中心地址
      host: localhost
      port: 8500
      discovery:
        hostname: 127.0.0.1
        service-name: ${spring.application.name}
```

3.启动类：

```java
@SpringBootApplication
@EnableDiscoveryClient
public class OrderConsulMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderConsulMain80.class, args);
    }
}
```

4.配置bean

```java
@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getTemplate(){
        return new RestTemplate();
    }
}
```

5.controller

```java
@RestController
public class PaymentController {
    private String url = "http://CLOUD-PROVIDER-PAYMENT";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("consumer/payment/consul")
    public String cpaymentConsul() {
        String forObject = restTemplate.getForObject(url + "/payment/consul", String.class);
        return forObject;
    }
}
```

# 十、CAP理论

C:Consistency（强一致性）

A：Availablity（可用性）

P:Parttition tolerance（分区容错性）

CAP理论关注的粒度时数据而非系统整体架构



AP：eureka

CP：Zookeeper Consul

![image-20200419160556200](assets/image-20200419160556200.png)

![image-20200419160636686](assets/image-20200419160636686.png)

# 十一、Ribbon负载均衡调用

## 11.1简介

Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡工具。主要是提供客户端的负载均衡和服务调用。

官网地址：https://github.com/Netflix/ribbon/wiki/Getting-Started

**但是很遗憾，Ribbon已进入维护模式。未来可以考虑使用SpringCloud loadBalancer替代方案**

## 11.2可以用来做什么

负载均衡：将用户的请求平均分配到多个服务上，从而达到系统的高可用。常见的负载均衡软件有Nginx、LVS、硬件F5等。

Ribbon和Nginx负载均衡的区别：

Nginx是服务器端的负载均衡，客户端的所有请求都会交给服务器端的Nginx。由Nginx实现转发请求。即负载均衡由服务器端实现的。

Ribbon时本地负载均衡，在调用服务接口时，会在注册中心上获取服务列表混存到JVM本地实现负载均衡的调用远程服务。

架构说明：

![image-20200418171914125](assets/image-20200418171914125.png)

Rabbin包无需单独引入。已经在Eureka中包含

## Ribbon核心组件IRule

根据特定的算法从服务器列表中选取一个要访问的对象服务

1.com.netflix.loadbalancer.RoundRobinRule 轮询

2.com.netflix.loadbalancer.RandomRule 随机

3.com.netflix.loadbalancer.RetryRule 先按照RoundRobinRule的策略获取服务,如果获取服务失败则在指定时间内进行重试,获取可用的服务

4.WeightedResponseTimeRule 对RoundRobinRule的扩展,响应速度越快的实例选择权重越多大,越容易被选择

5.BestAvailableRule 会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务,然后选择一个并发量最小的服务

6.AvailabilityFilteringRule 先过滤掉故障实例,再选择并发较小的实例

7.ZoneAvoidanceRule 默认规则,复合判断server所在区域的性能和server的可用性选择服务器

## 修改负载均衡规则

```java
@Configuration
public class MySelfRule {

    @Bean
    public IRule myRule() {
        // 定义为随机
        return new RoundRobinRule();
    }
}

```

 启动类添加：

```java
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "CLOUD-PAYMENT-SERVICE", configuration = MySelfRule.class)
public class OrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class, args);
    }
}
```

## 负载均衡算法



# 十二、RestTmplate

![image-20200418172029370](assets/image-20200418172029370.png)

![image-20200418172039660](assets/image-20200418172039660.png)

![image-20200418172043634](assets/image-20200418172043634.png)

# 十三、OpenFeign服务接口调用

## 简介

OpenFeign是一个声明式Web服务客户端，只需要创建一个接口，即可实现Web远程调用，让编写Web服务客户端变得简单

官网：https://github.com/spring-cloud/spring-cloud-openfeign

### Feign和Open Feign的区别：

![image-20200419160849350](assets/image-20200419160849350.png)

## Open Feign使用步骤

1.pom

```
        <!--openfeign-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
```

2.yml

```yml
server:
  port: 80
eureka:
  client:
    register-with-eureka: false
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
```

3.主启动类

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class OrderFeignMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderFeignMain80.class, args);
    }
}
```

4.业务层

```java
@FeignClient("CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {

    @GetMapping("/payment/getPaymentById/{id}")
    public CommonResult getPaymentById(@PathVariable("id") long id);
}
```

5.controller

```java
@RestController
public class OrderFeignController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}",produces = "application/json;charset=UTF-8")
    public CommonResult getPaymentById(@PathVariable("id") long id) {
        return paymentFeignService.getPaymentById(id);
    }
}
```

## OpenFeign超时控制

OpenFeign默认等待一秒钟，超时后报错。

超时控制：

```yml
server:
  port: 80
eureka:
  client:
    register-with-eureka: false
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
# 设置feign客户端超时时间(OpenFeign默认支持ribbon)
ribbon:
  # 指的是建立连接所用的时间,适用于网络状态正常的情况下,两端连接所用的时间
  ReadTimeout: 5000
  # 指的是建立连接后从服务器读取到可用资源所用的时间
  ConnectTimeout: 5000
```

## OpenFeign日志打印功能

OpenFeign支持日志打印功能，可以让我们看到HTTP调用的具体细节。

日志级别：

![image-20200419164105824](assets/image-20200419164105824.png)

配置

```java
@Configuration
public class FeignConfig {

    /**
     * feignClient配置日志级别
     *
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        // 请求和响应的头信息,请求和响应的正文及元数据
        return Logger.Level.FULL;
    }
}

```

yml开启日志

```yml
server:
  port: 80
eureka:
  client:
    register-with-eureka: false
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
# 设置feign客户端超时时间(OpenFeign默认支持ribbon)
ribbon:
  # 指的是建立连接所用的时间,适用于网络状态正常的情况下,两端连接所用的时间
  ReadTimeout: 5000
  # 指的是建立连接后从服务器读取到可用资源所用的时间
  ConnectTimeout: 5000
logging:
  level:
    # feign日志以什么级别监控哪个接口
    com.atguigu.springcloud.service.PaymentFeignService: debug
```

# 十四、Hystrix熔断器

## 概述：

![image-20200419164322429](assets/image-20200419164322429.png)

能干什么：

服务降级

服务熔断

接近实时的监控

官网：https://github.com/Netflix/hystrix/wiki

**但是Hystrix宣布停更进入维护状态。**

## 重要概念

### 1.服务降级

不让客户端等待，可以立即返回一个有好的提示。

哪些情况会触发降级：

1.程序运行异常

2.超时

3.服务熔断触发降级

4.线程池/信号量也会导致服务降级

### 2.服务熔断

类似保险丝，当服务导入到最大的服务访问时，直接拒绝访问，然后调用服务降级

### 3.服务限流

秒杀高并发等操作，严禁一窝蜂的过来拥挤，大家排队，一秒钟处理多少个，有序进行。

## 使用

1.pom

```maven
        <!--hystrix-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>
```

2.yml

```yml
server:
  port: 8001
spring:
  application:
    name: cloud-provider-hystrix-payment
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7002.com:7002/eureka
```

3.主启动类

```java
@SpringBootApplication
@EnableEurekaClient
public class PaymentHystrixMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentHystrixMain8001.class, args);
    }
}
```



tomcat的默认工作线程数被打满了,没有多余的线程来分解压力和处理

## 新建80加入，远程访问8001





# 十五、Jmenter

### Jmeter设置为中文

找到jmeter下的bin目录，打开jmeter.properties 文件

第三十七行修改为

language=zh_CN

去掉前面的#，**以后打开就是中文界面了**

## 添加线程组

开启Jmeter,来20000个并发压死8001,20000个请求都去访问paymentInfo_TimeOut服务

![image-20200419170311899](assets/image-20200419170311899.png)

添加HTTP请求

![image-20200419170552166](assets/image-20200419170552166.png)













