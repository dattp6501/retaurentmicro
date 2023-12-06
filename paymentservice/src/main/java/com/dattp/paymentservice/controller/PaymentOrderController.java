package com.dattp.paymentservice.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dattp.paymentservice.config.ApplicationConfig;
import com.dattp.paymentservice.entity.Booking;
import com.dattp.paymentservice.service.BookingService;
import com.dattp.paymentservice.service.PaymentOrderService;

@RestController
@RequestMapping("/api/payment/user")
public class PaymentOrderController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired
    private KafkaTemplate<String,Long> kafkaTemplateLong;
    
    @GetMapping("/create_payment/{booking_id}")
    public ResponseEntity<String> createPayment(@PathVariable("booking_id") long bookingId, HttpServletRequest request) throws UnsupportedEncodingException{
        Booking booking = bookingService.getById(bookingId);
        if(booking.getState() == ApplicationConfig.PAID_STATE)
            return ResponseEntity.ok().body(
            "Hóa đơn đã được thanh toán"
        );
        String url = paymentOrderService.createURLVNPayPayment(booking, null, request.getRemoteAddr());
        return ResponseEntity.ok().body(
            "<a href=\""+url+"\">THANH TOÁN NGAY</a>"
        );
    }

    @GetMapping("/payment_return")
    public ResponseEntity<String> returnURLPayment(HttpServletRequest request) throws UnsupportedEncodingException{
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
        String signValue = paymentOrderService.hashAllFields(fields);
        if (!signValue.equals(vnp_SecureHash)) {
            return ResponseEntity.ok().body(
                "Dữ liệu đã bị sửa đổi"
            );
        }
        JSONObject data = new JSONObject();
        data.put("date", request.getParameter("vnp_PayDate"));
        data.put("amount", request.getParameter("vnp_Amount"));
        data.put("booking_id", request.getParameter("vnp_TxnRef"));
        data.put("bank_code", request.getParameter("vnp_BankCode"));
        data.put("description", request.getParameter("vnp_OrderInfo"));
        try {
            if(request.getParameter("vnp_BankTranNo")==null){
                data.put("bank_tran_code", "");
            }else{
                data.put("bank_tran_code", request.getParameter("vnp_BankTranNo"));
            }
        } catch (Exception e) {
            data.put("bank_tran_code", "");
        }
        try {
            if(request.getParameter("vnp_TransactionNo")==null){
                data.put("vnpay_tran_code", "");
            }else{
                data.put("vnpay_tran_code", request.getParameter("vnp_TransactionNo"));
            }
        } catch (Exception e) {
            data.put("vnpay_tran_code", "");
        }
        // kiem tra giao dich co thanh cong khong
        if(!request.getParameter("vnp_TransactionStatus").equals("00")){
            data.put("code", 300);
            data.put("description", "Giao dịch không thành công");
        }else{
            bookingService.updateState(Long.parseLong(data.getString("booking_id")), ApplicationConfig.PAID_STATE);
            kafkaTemplateLong.send("paymentOrderSuccess", Long.parseLong(data.getString("booking_id")));
            data.put("code",200);
            data.put("description","Thanh toán thành công");
        }
        return ResponseEntity.ok().body(
            generateFormNotiPayment(data)
        );
    }
    private String generateFormNotiPayment(JSONObject data){
        if(data.getInt("code")==-200) return
        "<!DOCTYPE html>"
        +"<html lang=\"en\">"
        +"    <head>"
        +"        <meta charset=\"UTF-8\">"
        +"        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
        +"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
        +"        <title>Thanh toán đơn hàng</title>"
        +"        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">"
        +"        <script src=\"https://kit.fontawesome.com/e7c3a3eb40.js\" crossorigin=\"anonymous\"></script>"
        +"    </head>"
        +"    <body>"
        +"        <div class=\"vh-100 d-flex justify-content-center align-items-center\">"
        +"            <div class=\"col-md-7\">"
        +"                <div class=\"border border-3 border-danger\"></div>"
        +"                <div class=\"card  bg-white shadow p-5\">"
        +"                    <div class=\"mb-4 text-center\">"
        +"                        <i class=\"fa-regular fa-circle-xmark fa-beat\" style=\"color: #f43434;font-size:75px;\"></i>"
        +"                    </div>"
        +"                    <div class=\"text-center\">"
        +"                        <h1>"+data.getString("description")+"</h1>"
        +"                    </div>"
        +"                </div>"
        +"            </div>"
        +"        </div>"
        +"    </body>"
        +"</html>";

        
        if(data.getInt("code")==200) return 
        "<!DOCTYPE html>"
        +"<html lang=\"en\">"
        +"    <head>"
        +"        <meta charset=\"UTF-8\">"
        +"        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
        +"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
        +"        <title>Thanh toán đơn hàng</title>"
        +"        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">"
        +"        <script src=\"https://kit.fontawesome.com/e7c3a3eb40.js\" crossorigin=\"anonymous\"></script>"
        +"    </head>"
        +"    <body>"
        +"        <div class=\"vh-100 d-flex justify-content-center align-items-center\">"
        +"            <div class=\"col-md-7\">"
        +"                <div class=\"border border-3 border-success\"></div>"
        +"                <div class=\"card  bg-white shadow p-5\">"
        +"                    <div class=\"mb-4 text-center\">"
        +"                        <i class=\"fa-solid fa-circle-check fa-beat\" style=\"color: #36d339;font-size:75px;\"></i>"
        +"                    </div>"
        +"                    <div class=\"text-center\">"
        +"                        <h1>"+data.getString("description")+" !</h1>"
        +"                        <div class=\"col-md-12\" style=\"margin:30px auto;\">"
        +"                            <div class=\"row\">"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Mã đơn hàng: #"+data.getInt("booking_id")+"</p>"
        +"                                </div>"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Tổng tiền: "+data.getLong("amount")/100+"</p>"
        +"                                </div>"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Ngày: "+data.getString("date")+"</p>"
        +"                                </div>"
        +"                            </div>"
        +"                            <div class=\"row\">"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Mã ngân hàng: "+data.getString("bank_code")+"</p>"
        +"                                </div>"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Mã GD VNPAY: "+data.getString("vnpay_tran_code")+"</p>"
        +"                                </div>"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Mã GD ngân hàng: "+data.getString("bank_tran_code")+"</p>"
        +"                                </div>"
        +"                            </div>"
        +"                        </div>"
        // +"                        <a class=\"btn btn-outline-success\" href=\"#/dashboard\">Back Home</a>"
        +"                    </div>"
        +"                </div>"
        +"            </div>"
        +"        </div>"
        +"    </body>"
        +"</html>";
        // error
        return 
        "<!DOCTYPE html>"
        +"<html lang=\"en\">"
        +"    <head>"
        +"        <meta charset=\"UTF-8\">"
        +"        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
        +"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
        +"        <title>Thanh toán đơn hàng</title>"
        +"        <link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css\" rel=\"stylesheet\">"
        +"        <script src=\"https://kit.fontawesome.com/e7c3a3eb40.js\" crossorigin=\"anonymous\"></script>"
        +"    </head>"
        +"    <body>"
        +"        <div class=\"vh-100 d-flex justify-content-center align-items-center\">"
        +"            <div class=\"col-md-7\">"
        +"                <div class=\"border border-3 border-danger\"></div>"
        +"                <div class=\"card  bg-white shadow p-5\">"
        +"                    <div class=\"mb-4 text-center\">"
        +"                        <i class=\"fa-regular fa-circle-xmark fa-beat\" style=\"color: #f43434;font-size:75px;\"></i>"
        +"                    </div>"
        +"                    <div class=\"text-center\">"
        +"                        <h1>"+data.getString("description")+" !</h1>"
        +"                        <div class=\"col-md-12\" style=\"margin:30px auto;\">"
        +"                            <div class=\"row\">"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Mã đơn hàng: "+data.getInt("booking_id")+"</p>"
        +"                                </div>"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Tổng tiền: "+data.getLong("amount")/100+"</p>"
        +"                                </div>"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Ngày: "+data.getString("date")+"</p>"
        +"                                </div>"
        +"                            </div>"
        +"                            <div class=\"row\">"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Mã ngân hàng: "+data.getString("bank_code")+"</p>"
        +"                                </div>"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Mã GD VNPAY: "+data.getString("vnpay_tran_code")+"</p>"
        +"                                </div>"
        +"                                <div class=\"col-md-4\" style=\"text-align:left;\">"
        +"                                    <p>Mã GD ngân hàng: "+data.getString("bank_tran_code")+"</p>"
        +"                                </div>"
        +"                            </div>"
        +"                        </div>"
        // +"                        <a class=\"btn btn-outline-success\" href=\"#/dashboard\">Back Home</a>"
        +"                    </div>"
        +"                </div>"
        +"            </div>"
        +"        </div>"
        +"    </body>"
        +"</html>";
    }
}