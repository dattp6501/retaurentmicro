package com.dattp.paymentservice.utils.payment;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.dattp.paymentservice.entity.HistoryPayment;

public class PaymentDefault implements Payment{
    public PaymentDefault() throws Exception{
        throw new Exception("Phương thức thanh toán DEFAULT không hỗ trợ trên trình duyệt");
    }

    @Override
    public String createURLPayment(long id, Date date, float money, long userId, String gmail, String fullname, String bankCode1, String ipAddress) throws UnsupportedEncodingException {
        return null;
    }

    @Override
    public HistoryPayment getInfoPayment(HttpServletRequest request) throws UnsupportedEncodingException {
        return null;
    }
    
}
