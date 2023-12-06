# retaurentmicroservices
# circuit breaker reactive microservice spring boot kafka
# run kafka
D:\kafka\kafka_2.13-3.6.0\bin\windows\zookeeper-server-start.bat D:\kafka\kafka_2.13-3.6.0\config\zookeeper.properties

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-server-start.bat D:\kafka\kafka_2.13-3.6.0\config\server.properties

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-topics.bat --create --topic product --bootstrap-server localhost:9092

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-console-producer.bat --topic createBookingTopic --bootstrap-server localhost:9092

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-console-consumer.bat --topic newOrder --from-beginning --bootstrap-server localhost:9092

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-console-consumer.bat --topic checkOrder --from-beginning --bootstrap-server localhost:9092

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-console-consumer.bat --topic notiOrder --from-beginning --bootstrap-server localhost:9092


D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-console-consumer.bat --topic createPaymentOrder --from-beginning --bootstrap-server localhost:9092

# system architecture
![alt](./arch.png)
# feature
# ==================================== USER ========================================================
LOGIN
![alt](./feature/login.png)
REGISTER
![alt](./feature/register.png)
# ====================================== MANAGE ======================================================
BOOKING MANAGE
![alt](./feature/booking-manage.png)
UPDATE TABLE
![alt](./feature/update-table.png)
# 
 SOA, Microservice, DDD pattern, API base
 saga : choreography và orchestration.