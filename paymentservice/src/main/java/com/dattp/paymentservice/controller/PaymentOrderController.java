package com.dattp.paymentservice.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dattp.paymentservice.config.ApplicationConfig;
import com.dattp.paymentservice.dto.ResponseDTO;
import com.dattp.paymentservice.entity.Booking;
import com.dattp.paymentservice.entity.HistoryPayment;
import com.dattp.paymentservice.service.BookingService;
import com.dattp.paymentservice.service.HistoryPaymentService;
import com.dattp.paymentservice.utils.payment.Payment;
import com.dattp.paymentservice.utils.payment.PaymentFactory;
import com.dattp.paymentservice.utils.payment.PaymentType;


@Controller
@RequestMapping("/api/payment/user")
public class PaymentOrderController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private HistoryPaymentService historyPaymentService;

    @Autowired
    private KafkaTemplate<String,Long> kafkaTemplateLong;
    
    @GetMapping("/infor_booking/{booking_id}")
    public String getInfoBooking(@PathVariable("booking_id") long bookingId, Model model){
        Booking booking = bookingService.getById(bookingId);
        if(booking==null){
            model.addAttribute("message", "Phiếu đặt không tồn tại");
            return "payment_error";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        model.addAttribute("dateFormat", dateFormat);
        model.addAttribute("booking", booking);
        return "infor_booking";
    }

    // get option payment
    @PostMapping("/create_payment")
    public String createPayment(@RequestParam("id") long bookingId , @RequestParam("type") String type, Model model, HttpServletRequest request) throws Exception{
        Booking booking = bookingService.getById(bookingId);
        if(booking==null){
            model.addAttribute("message", "Phiếu không tồn tại");
            return "payment_error";
        }
        if(booking.getState() == ApplicationConfig.PAID_STATE){
            model.addAttribute("message", "Phiếu đặt đã được thanh toán");
            return "payment_error";
        }
        // payment
        try {
            Payment payment;
            if(type.toUpperCase().equals(PaymentType.VNPAY.value())) 
                payment = PaymentFactory.getPayment(PaymentType.VNPAY);
            else payment = PaymentFactory.getPayment(PaymentType.DEFAULT);
            return "redirect:"+payment.createURLPayment(
                booking.getId(), 
                booking.getDate(), 
                booking.getDeposits(), 
                booking.getCustomerId(), 
                booking.getEmail(), booking.getCustemerFullname(), 
                null, 
                request.getRemoteAddr()
            );
        } catch (Exception e) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            model.addAttribute("dateFormat", dateFormat);
            model.addAttribute("booking", booking);
            model.addAttribute("message", e.getMessage());
            return "infor_booking";
        }
    }


    @GetMapping("/vnpay_payment_return")
    @Transactional
    public String returnURLPayment(Model model, HttpServletRequest request) throws Exception{
        Payment payment = PaymentFactory.getPayment(PaymentType.VNPAY);
        HistoryPayment data = payment.getInfoPayment(request);
        if(data.getCode()==HttpStatus.OK.value()){
            historyPaymentService.save(data);
            long customerId = bookingService.getById(data.getBookingId()).getCustomerId();
            data.setCustomerId(customerId);
            bookingService.updateState(data.getBookingId(), ApplicationConfig.PAID_STATE);
            kafkaTemplateLong.send("paymentOrderSuccess", data.getBookingId());
        }
        model.addAttribute("data", data);
        return "payment_response";
    }

    @GetMapping("/history_payment")
    @RolesAllowed("ROLE_ORDER_NEW")
    @ResponseBody
    public ResponseEntity<ResponseDTO> hello(Pageable pageable){
        long customerId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        List<HistoryPayment> list = historyPaymentService.getAllByCustomerId(customerId, pageable);
        return ResponseEntity.ok().body(
            new ResponseDTO(HttpStatus.OK.value(), "Thành công", list)
        );
    }
}