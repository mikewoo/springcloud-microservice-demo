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

注意： zipkin使用ES这类数据库做持久化之后，是不会自动生成服务之间的依赖关系图
官方提供了依赖图生成工具，请参考：https://github.com/openzipkin/zipkin-dependencies

启动命令如下：
STORAGE_TYPE=elasticsearch ES_HOSTS=192.168.33.100:9200 java -jar zipkin-dependencies.jar

