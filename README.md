# retaurentmicroservices
# circuit breaker reactive microservice spring boot kafka
# run kafka
D:\kafka\kafka_2.13-3.6.0\bin\windows\zookeeper-server-start.bat D:\kafka\kafka_2.13-3.6.0\config\zookeeper.properties

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-server-start.bat D:\kafka\kafka_2.13-3.6.0\config\server.properties

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-topics.bat --create --topic product --bootstrap-server localhost:9092

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-console-producer.bat --topic createBookingTopic --bootstrap-server localhost:9092

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-console-consumer.bat --topic newOrder --from-beginning --bootstrap-server localhost:9092

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-console-consumer.bat --topic resultCheckBookingTopic --from-beginning --bootstrap-server localhost:9092


# referrence
https://www.youtube.com/watch?v=SqVfCyfCJqw
https://developer.okta.com/blog/2022/09/15/kafka-microservices#what-is-kafka




# 
 SOA, Microservice, DDD pattern, API base
 saga : choreography và orchestration.