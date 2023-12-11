package com.dattp.paymentservice.utils.payment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import com.dattp.paymentservice.entity.HistoryPayment;

public class PaymentVNPay implements Payment{
    // pijir13267@notedns.com Admin$123
    // https://sandbox.vnpayment.vn/merchantv2/
    // https://sandbox.vnpayment.vn/vnpaygw-sit-testing/user/login
    // @Value("${vnpay.vnp_TmnCodeSource}")
    // private String vnp_TmnCodeSource;// dang ky moi co 9704198526191432190

    // @Value("${vnpay.vnp_HashSecret}")
    // private String vnp_HashSecret;// dang ky moi co

    // @Value("${vnpay.vnp_PayUrl}")
    // private String vnp_PayUrl;// trang thanh toan

    // @Value("${vnpay.vnp_apiUrl}")
    // private String vnp_apiUrl;

    // @Value("${vnpay.vnp_Returnurl}")
    // private String vnp_Returnurl;
    private String vnp_TmnCodeSource="AJX43VFX";// dang ky moi co 9704198526191432190
    private String vnp_HashSecret="HHQWMDFGAETUFCFARRSQSTACWBILSVPF";// dang ky moi co
    private String vnp_PayUrl="https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";// trang thanh toan
    // private String vnp_apiUrl="https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";
    private String vnp_Returnurl="http://localhost:9004/api/payment/user/vnpay_payment_return";

    public PaymentVNPay(){};
    
    private String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }
    public String hashAllFields(Map<String,String> fields) {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder sb = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                sb.append(fieldName);
                sb.append("=");
                sb.append(fieldValue);
            }
            if (itr.hasNext()) {
                sb.append("&");
            }
        }
        return hmacSHA512(vnp_HashSecret, sb.toString());
    }


    @Override
    public String createURLPayment(long id, Date date, float money, long userId, String gmail, String fullname, String bankCode1, String ipAddress) throws UnsupportedEncodingException {
        String vnp_TxnRef = id+"";// id don hang cua minh
        long amount =(long) money;
        String orderType = "other";
        String bankCode = bankCode1;
        String vnp_IpAddr = ipAddress;
        String vnp_TmnCode = vnp_TmnCodeSource;//
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        // ====================== tehm du lieu de goi api vnpay
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100));
        vnp_Params.put("vnp_CurrCode", "VND");

        if (bankCode != null && !bankCode.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef+"////////"+userId);
        vnp_Params.put("vnp_OrderType", orderType);
        // ngon ngu
        // String locate = null;
        // if (locate != null && !locate.isEmpty()) {
        //     vnp_Params.put("vnp_Locale", locate);
        // } else {
        //     vnp_Params.put("vnp_Locale", "vn");
        // }
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        // try {
        //     vnp_Params.put("vnp_Bill_Mobile", objReq.getString("vnp_Bill_Mobile"));// vd: 84932224546
        // } catch (Exception e) {
        // }
        try {
            vnp_Params.put("vnp_Bill_Email", gmail);
        } catch (Exception e) {
        }
        // try {
        //     vnp_Params.put("vnp_ExpireDate", objReq.getString("vnp_ExpireDate"));// thoi gian het han thanh toan
        //                                                                             // yyyyMMddHHmmss
        // } catch (Exception e) {
        // }
        try {
            // vnp_Params.put("vnp_Bill_FirstName", objReq.getString("vnp_Bill_FirstName"));
            vnp_Params.put("vnp_Bill_LastName", fullname);
        } catch (Exception e) {
        }
        try {
            // vnp_Params.put("vnp_Bill_Country", objReq.getString("vnp_Bill_Country"));
            // vnp_Params.put("vnp_Bill_City", objReq.getString("vnp_Bill_City"));
            vnp_Params.put("vnp_Bill_Address", "Address user");

        } catch (Exception e) {
        }
        vnp_Params.put("vnp_Inv_Company", "Nhà hàng DATTP");

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        cld.setTime(date);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.HOUR_OF_DAY, 24);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnp_PayUrl + "?" + queryUrl;
        return paymentUrl;
    }

    @Override
    public HistoryPayment getInfoPayment(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String,String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
            String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = hashAllFields(fields);
        if (!signValue.equals(vnp_SecureHash)) {
            HistoryPayment historyPayment = new HistoryPayment();
            historyPayment.setCode(HttpStatus.BAD_REQUEST.value());
            historyPayment.setMessage("Dữ liệu đã bị sửa đổi");
            return historyPayment;
        }
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyyMMddHHmmss").parse(request.getParameter("vnp_PayDate"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HistoryPayment data = new HistoryPayment();
        data.setDate(format.format(date));
        data.setAmount(Float.parseFloat(request.getParameter("vnp_Amount"))/100);
        data.setBookingId(Long.parseLong(request.getParameter("vnp_TxnRef")));
        data.setBankCode(request.getParameter("vnp_BankCode"));
        data.setMessage(request.getParameter("vnp_OrderInfo"));
        try {
            data.setBankTranCode(request.getParameter("vnp_BankTranNo"));
        } catch (Exception e) {data.setBankTranCode("");}
        try {
            data.setTranCode(request.getParameter("vnp_TransactionNo"));
        } catch (Exception e) {data.setTranCode("");}
        // kiem tra giao dich co thanh cong khong
        if(!request.getParameter("vnp_TransactionStatus").equals("00")){
            data.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }else{
            data.setCode(HttpStatus.OK.value());
        }
        return data;
    }
}
