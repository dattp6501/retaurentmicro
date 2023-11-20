package com.dattp.notifitationservice.kafkalistener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dattp.notifitationservice.dto.UserRequestKafkaDTO;
import com.dattp.notifitationservice.service.SendMailService;

@Component
public class UserKafkaListener {
    @Autowired
    private SendMailService sendMailService;

    @Value("${mail.username}")
    private String MAIL_USERNAME;

    @Value("${mail.password}")
    private String MAIL_PASSWORD;

    @KafkaListener(topics = "new-user",groupId = "group1", containerFactory = "factoryUser")
    public void listenNewUser(UserRequestKafkaDTO userRequestKafkaDTO){
        System.out.println("======================   NEW USER   ========================");
        System.out.println(userRequestKafkaDTO.getUsername());
        System.out.println("===============================================================");
        sendMailService.sendOutlook(MAIL_USERNAME, MAIL_PASSWORD, userRequestKafkaDTO.getMail(), "Xác thực mail", "Xác thực tài khoản thành công");
    }
}