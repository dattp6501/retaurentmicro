package com.dattp.notifitationservice.service;

import org.springframework.stereotype.Service;

import com.dattp.notifitationservice.utils.EmailHelper;

@Service
public class SendMailService {
    public String sendOutlook(String From, String password, String to, String subTitle, String content){
        try {
            return EmailHelper.sendMailOutlook(From, to, subTitle, content, password);
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}