## zipkin-server启动命令

zipkin-server jar包下载地址：

https://search.maven.org/remote_content?g=io.zipkin.java&a=zipkin-server&v=LATEST&c=exec

启动命名如下：

java -DKAFKA_BOOTSTRAP_SERVERS=192.168.33.100:9092 -DSTORAGE_TYPE=elasticsearch -DES_HOSTS=192.168.33.100:9200 -jar zipkin-server-2.12.0-exec.jar --server.port=9411

参数说明：

KAFKA_BOOTSTRAP_SERVERS: kafka的ip和端口

STORAGE_TYPE: 存储数据方式可以为 mem, mysql, cassandra, elasticsearch

ES_HOSTS: es的url 

详细请参照官方github https://github.com/openzipkin/zipkin/tree/master/zipkin-server

启动后访问url: http://***:9411/zipkin

注意： zipkin使用ES这类数据库做持久化之后，是不会自动生成服务之间的依赖关系图。

官方提供了依赖图生成工具，请参考：https://github.com/openzipkin/zipkin-dependencies

依赖图生成工具启动命令如下：

STORAGE_TYPE=elasticsearch ES_HOSTS=192.168.33.100:9200 java -jar zipkin-dependencies.jar


## 配置中心仓库

github: https://github.com/mikewoo/microservice-demo-config.git


## 构建Spring Cloud微服务Docker镜像，并上传至阿里云镜像仓库

### 以microservice-eureka-server注册中心服务为例

* 1 添加pom配置文件

    ```xml
    # 添加docker.image.prefix属性
    <properties>
        <docker.image.prefix>mikewoo</docker.image.prefix>
    </properties>
    
    # 添加dockerfile-maven-plugin插件
    <build>
        <finalName>microservice-eureka-server</finalName>
        <plugins>
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>dockerfile-maven-plugin</artifactId>
                <version>1.4.9</version>
                <configuration>
                    <repository>${docker.image.prefix}/${project.artifactId}</repository>
                    <buildArgs>
                        <JAR_FILE>target/${project.build.finalName}.jar</JAR_FILE>
                    </buildArgs>
                </configuration>
            </plugin>
        </plugins>
    </build>
    ```

* 2 在microservice-eureka-server工程中创建Dockerfile文件

    内容如下：
    
    ```text
    FROM openjdk:8-jdk-alpine
    VOLUME /tmp
    ARG JAR_FILE
    COPY ${JAR_FILE} microservice-eureka-server.jar
    ENTRYPOINT ["java","-Xmx110m -Xms110m -jar","/microservice-eureka-server.jar"]
    ```

* 3 构建Docker镜像
  
  执行mvn install dockerfile:build命令构建Docker镜像
  
* 4 登录阿里云Docker Registry
  ```text
  $ sudo docker login --username=mikewoo registry.cn-shanghai.aliyuncs.com
  ```
  
* 5 将镜像推送到Registry
  ```text
  $ sudo docker tag [ImageId] registry.cn-shanghai.aliyuncs.com/mikewoo/microservice-eureka-server:[镜像版本号]
  $ sudo docker push registry.cn-shanghai.aliyuncs.com/mikewoo/microservice-eureka-server:[镜像版本号]
  ```
  
* 6 从Registry中拉取镜像
  ```text
  $ sudo docker pull registry.cn-shanghai.aliyuncs.com/mikewoo/microservice-eureka-server:[镜像版本号]
  ```
  
* 7 运行镜像为一个在后台执行的容器
  ```text
  $ sudo docker container run --name "spring-docker-demo" -d -p 8761:8761 registry.cn-shanghai.aliyuncs.com/mikewoo/microservice-eureka-server:[镜像版本号]
  ```
  
* 8 查看容器运行日志
  ```text
  $ sudo docker logs -f [容器ID]
  ```