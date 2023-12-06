package com.dattp.notifitationservice.kafkalistener;

import java.text.NumberFormat;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.dattp.notifitationservice.dto.kafka.BookedTableRequestKafkaDTO;
import com.dattp.notifitationservice.dto.kafka.BookingRequestKafkaDTO;
import com.dattp.notifitationservice.service.SendMailService;

@Component
public class BookingKafkaListener {
    @Autowired
    private SendMailService sendMailService;

    @Value("${payment.url}")
    private String urlPayment;

    @Value("${mail.username}")
    private String MAIL_USERNAME;

    @Value("${mail.password}")
    private String MAIL_PASSWORD;

    // sent after confirmation by the manager(orderservice send)
    @KafkaListener(topics = "notiOrder", groupId = "group1", containerFactory = "factoryBooking")
    public void listenerPaymentOrder(BookingRequestKafkaDTO req){
        sendMailService.sendOutlook(MAIL_USERNAME, MAIL_PASSWORD, "dattp.b19at040@stu.ptit.edu.vn", "THÔNG BÁO ĐẶT BÀN", createContentMailBooking(req));
    }
    
    private String createContentMailBooking(BookingRequestKafkaDTO req){
        String style = 
        "<style>"
        +"table {"
        +"  font-family: arial, sans-serif;"
        +"  border-collapse: collapse;"
        +"  width: 100%;"
        +"}"
        +"td, th {"
        +"  border: 1px solid #dddddd;"
        +"  text-align: left;"
        +"  padding: 8px;"
        +"}"
        +"tr:nth-child(even) {"
        +"  background-color: #dddddd;"
        +"};"
        +"</style>";
        String html = 
        "<!DOCTYPE html>\n"
        + "<html lang=\"en\">\n"
        + "\n"
        + "<head>\n"
        + style
        + "</head>\n"
        + "\n"
        + "<body>\n"
        + "    <h3 style=\"color: blue;\">ĐẶT HÀNG THÀNH CÔNG</h3>\n"
        + "    <div>Khách hàng : "+req.getCustemerFullname().toUpperCase()+"</div>\n"
        + "    <div>Ngày đặt : "+req.getDate()+"</div>\n"
        + "    <div>Ghi chú : "+req.getDescription()+"</div>\n";
        // danh sach bàn
        String table = 
            "<table>"
            +"<tr>"
            +"  <th>Tên bàn</th>"
            +"  <th>Giá</th>"
            +"  <th>Trạng thái</th>"
            +"</tr>";
        for(BookedTableRequestKafkaDTO t : req.getBookedTables()){
            String tr =
            "<tr>"
            +"  <td>"+t.getName()+"</td>"
            +"  <td>"+formatNumberMonney(t.getPrice())+"</td>"
            +"  <td>"+t.getState()+"</td>"
            +"</tr>";
            table += tr;
        }
        
        // voucher
        // String vouchers = 
        // "<tr>"
        // +"  <td colspan=\"2\">"+"Voucher"+"</td>"
        // +"  <td>";
        // for(VoucherBooking v : booking.getVoucherBookings()){
        //     if(v.getType().equals("n")){
        //         vouchers += "-"+formatNumberMonney(v.getValue())+"<br/>";
        //     }else if(v.getType().equals("%")){
        //         vouchers += "-"+formatNumber(v.getValue())+"%"+"<br/>";
        //     }
        // }
        // vouchers = vouchers.substring(0, vouchers.length()-5);
        // vouchers+="</td>";
        // vouchers+="</tr>";
        // table += vouchers;
        // thanh tien
        table += "  <td colspan=\"2\">"+"Tiền cọc"+"</td>";
        table += "  <td>"+formatNumberMonney(req.getDeposits())+"</td>";
        // 
        table+="</table>";
        html += table;
        String linkPayment = "<a href=\""+urlPayment+"/"+req.getId()+"\">THANH TOÁN NGAY</a>";
        html+=linkPayment;
        // 
        html += "    <h3 style=\"color: blue;\">Cảm ơn bạn đã quan tâm tới chúng tôi</h3>\n"
        + "\n"
        + "</body>\n"
        + "\n"
        + "</html>";
        return html;
    }
    public static String formatNumberMonney(float number){
        return NumberFormat.getInstance(new Locale("vi", "VN")).format(number)+ " " 
        + NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).getCurrency().toString();
    }
    public static String formatNumber(float number){
        return NumberFormat.getInstance(new Locale("vi", "VN")).format(number);
    }
}
