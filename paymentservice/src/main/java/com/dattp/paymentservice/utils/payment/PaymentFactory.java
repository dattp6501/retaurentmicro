package com.dattp.paymentservice.utils.payment;

public class PaymentFactory {
    public static Payment getPayment(PaymentType paymentType) throws Exception{
        switch (paymentType) {
            case VNPAY:
                return new PaymentVNPay();
            default:
                return new PaymentDefault();
        }
    }
}
