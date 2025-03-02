package com.ecolink.spring.service;

import com.ecolink.spring.dto.PaymentRequest;
import com.ecolink.spring.dto.PaymentResponse;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        // Aquí iría la lógica para procesar el pago con el proveedor de pagos
        // Simulamos una respuesta exitosa
        PaymentResponse response = new PaymentResponse();
        response.setSuccessful(true);
        return response;
    }
}
