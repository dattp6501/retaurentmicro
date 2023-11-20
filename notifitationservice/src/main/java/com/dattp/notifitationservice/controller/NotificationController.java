package com.dattp.notifitationservice.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.notifitationservice.config.ApplicationConfig;
import com.dattp.notifitationservice.service.SendMailService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private SendMailService sendMailService;


    @PostMapping(value = "/send_with_outlook", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> sendEmailWithOutlook(@RequestBody String data){
        JSONObject respData = new JSONObject();
        HttpStatus status;
        try {
            JSONObject reqData = new JSONObject(data);
            String from = reqData.getString("from");
            String to = reqData.getString("to");
            String subTitle = reqData.getString("subtitle");
            String content = reqData.getString("content");
            String password = reqData.getString("password");

            String result = sendMailService.sendOutlook(from, password, to, subTitle, content);
            if(result.equals("OK")){
                respData.put("code", ApplicationConfig.OK);
                status = ApplicationConfig.HTTP_STATUS_OK;
            }else{
                respData.put("code", ApplicationConfig.DEFAULT);
                status = ApplicationConfig.HTTP_STATUS_INTERNAL_SERVER_ERROR;
            }
            respData.put("description", result);
        } catch (JSONException e) {
            respData.put("code", ApplicationConfig.DEFAULT);
            respData.put("description", e.getMessage());
            status = ApplicationConfig.HTTP_STATUS_BAD_REQUEST;
        }
        return new ResponseEntity<>(respData.toString(), status);
    }
}
