package com.dattp.paymentservice.utils.payment;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.dattp.paymentservice.entity.HistoryPayment;

public interface Payment {
    String createURLPayment(long id, Date date, float money, long userId, String gmail, String fullname, String bankCode1, String ipAddress) throws UnsupportedEncodingException;
    HistoryPayment getInfoPayment(HttpServletRequest request) throws UnsupportedEncodingException;
}