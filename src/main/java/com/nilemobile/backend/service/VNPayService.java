package com.nilemobile.backend.service;

import com.nilemobile.backend.config.VNPayConfig;
import com.nilemobile.backend.reponse.PaymentDTO;
import com.nilemobile.backend.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class VNPayService {

    private final VNPayConfig vnPayConfig;

    public VNPayService(VNPayConfig vnPayConfig) {
        this.vnPayConfig = vnPayConfig;
    }

    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {
//        long amount = Long.parseLong(request.getParameter("amount")) * 100L;
//
//        String bankCode = request.getParameter("bankCode");
//        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
//        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
//        if (bankCode != null && !bankCode.isEmpty()) {
//            vnpParamsMap.put("vnp_BankCode", bankCode);
//        }
//
//        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
//        System.out.println("vnpParamsMap: " + vnpParamsMap);
//        //build queyUrl
//        String queryUrl = VNPayUtil.getPaymentUrl(vnpParamsMap, true);
//        String hashData = VNPayUtil.getPaymentUrl(vnpParamsMap, false);
//
//        queryUrl += "&vnp_SecureHash=" + VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
//
//        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
//        return PaymentDTO.VNPayResponse.builder().code("ok").message("Success").paymentUrl(paymentUrl).build();
        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VNPayUtil.getPaymentUrl(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentUrl(vnpParamsMap, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }


}
