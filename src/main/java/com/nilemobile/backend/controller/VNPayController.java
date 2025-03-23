package com.nilemobile.backend.controller;

import com.nilemobile.backend.reponse.PaymentDTO;
import com.nilemobile.backend.service.VNPayService;
import com.sun.net.httpserver.Authenticator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment-vnpay")
public class VNPayController {
    private final VNPayService vnPayService;

    public VNPayController(VNPayService vnPayService) {
        this.vnPayService = vnPayService;
    }


    @GetMapping
    public ResponseEntity<?> pay(HttpServletRequest request) {
        return new ResponseEntity<>(vnPayService.createVnPayPayment(request), HttpStatus.OK);
    }

    @GetMapping("/callback")
    public ResponseEntity<?> payCallbackHanler(HttpServletRequest request,
                                               HttpServletResponse response,
                                               @RequestParam String vnp_ResponseCode) {
        if (vnp_ResponseCode == null) {
            PaymentDTO.VNPayResponse responseBody = PaymentDTO.VNPayResponse.builder()
                    .code("99")
                    .message("Missing vnp_ResponseCode")
                    .paymentUrl("")
                    .build();
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
//        response.sendRedirect("/");
        if (vnp_ResponseCode.equals("00")) {
            PaymentDTO.VNPayResponse responseBody = PaymentDTO.VNPayResponse.builder()
                    .code("00")
                    .message("success")
                    .paymentUrl("")
                    .build();
            return new ResponseEntity<>(responseBody, HttpStatus.OK);
        } else {
            PaymentDTO.VNPayResponse responseBody = PaymentDTO.VNPayResponse.builder()
                    .code(vnp_ResponseCode)
                    .message("failed")
                    .paymentUrl("")
                    .build();
            return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
        }
    }

}
