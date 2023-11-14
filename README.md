# retaurentmicroservices
https://www.youtube.com/watch?v=SqVfCyfCJqw
#run kafka
D:\kafka\kafka_2.13-3.6.0\bin\windows\zookeeper-server-start.bat D:\kafka\kafka_2.13-3.6.0\config\zookeeper.properties

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-server-start.bat D:\kafka\kafka_2.13-3.6.0\config\server.properties

D:\kafka\kafka_2.13-3.6.0\bin\windows\kafka-topics.bat --create --topic product --bootstrap-server localhost:9092