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
HOME
![alt](./feature/home.png)
DISH
![alt](./feature/search_dish.png)
![alt](./feature/dish_detail.png)
![alt](./feature/dish_comment.png)
TABLE
![alt](./feature/search_table_freetime.png)
![alt](./feature/table_detail.png)
CART
![alt](./feature/cart.png)
BOOKING DETAIL
![alt](./feature/booking-detail.png)
PAYMENT
![alt](./feature/payment_order.png)
![alt](./feature/payment_success.png)
![alt](./feature/payment_error.png)
NOTIFICATION
![alt](./feature/noti_order_mail.png)
# ====================================== MANAGE ======================================================
BOOKING MANAGE
![alt](./feature/manage_list_booking.png)
![alt](./feature/booking-manage.png)
TABLE MANAGE
![alt](./feature/manage_list_table.png)
![alt](./feature/manage_table_detail.png)
DISH MANAGE
![alt](./feature/manage_list_dish.png)
![alt](./feature/manage_list_dish.png)
<!-- VOUCHER MANAGE
![alt](./feature/) -->
# 
 SOA, Microservice, DDD pattern, API base
 saga : choreography và orchestration.