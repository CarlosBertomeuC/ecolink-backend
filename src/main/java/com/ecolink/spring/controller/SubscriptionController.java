package com.ecolink.spring.controller;

import com.ecolink.spring.entity.SubscriptionType;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.service.PaymentService;
import com.ecolink.spring.service.SubscriptionService;
import com.ecolink.spring.service.UserBaseService;
import com.ecolink.spring.dto.PaymentRequest;
import com.ecolink.spring.dto.PaymentResponse;
import com.ecolink.spring.entity.Subscription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;


import com.ecolink.spring.repository.UserBaseRepository;
import org.hibernate.Hibernate;


import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {
    @Autowired
    private PaymentService paymentService;


    private final SubscriptionService subscriptionService;
    private final UserBaseRepository userBaseRepository;

    public SubscriptionController(SubscriptionService subscriptionService, UserBaseService userBaseService, UserBaseRepository userBaseRepository) {
        this.subscriptionService = subscriptionService;
        this.userBaseRepository = userBaseRepository;
    }
    @Operation(summary = "Activar una suscripción", description = "Activa una suscripción para un usuario específico.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Suscripción activada correctamente"),
        @ApiResponse(responseCode = "400", description = "Usuario no encontrado", content = @Content)
    })
    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribeUser(
            @AuthenticationPrincipal UserBase user, 
            @RequestParam SubscriptionType type, 
            @RequestParam int durationDays) {
        
        if (user == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        // Initialize the subscription collection
        UserBase userWithSubscription = userBaseRepository.findById(user.getId()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Hibernate.initialize(userWithSubscription.getSubscription());

        Subscription subscription = userWithSubscription.getSubscription();
        if (subscription == null) {
            subscription = new Subscription();
            subscription.setUser(userWithSubscription);
        }

        subscription.setType(type);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(durationDays));
        subscriptionService.save(subscription);

        return ResponseEntity.ok("Suscripción activada correctamente");
    }

    //Esto es para cancelar la suscripción desde el perfil del usuario
    @PostMapping("/cancel")
public ResponseEntity<String> cancelSubscription(@AuthenticationPrincipal UserBase user) {
    if (user == null) {
        return ResponseEntity.badRequest().body("Usuario no encontrado");
    }

    Subscription subscription = user.getSubscription();
    if (subscription == null) {
        return ResponseEntity.badRequest().body("No hay suscripción asociada al usuario");
    }

    // Cambia la suscripción a "FREE"
    subscription.setType(SubscriptionType.FREE);
    subscription.setStartDate(LocalDate.now());
    subscription.setEndDate(null); // O establece una fecha de finalización adecuada

    subscriptionService.save(subscription); // Guarda la suscripción actualizada

    return ResponseEntity.ok("Suscripción cancelada y cambiada a FREE");
}

    @Operation(summary = "Verificar estado de suscripción", description = "Comprueba si un usuario tiene una suscripción activa.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado de la suscripción", 
                     content = @Content(mediaType = "application/json", 
                     schema = @Schema(type = "boolean"))),
        @ApiResponse(responseCode = "400", description = "Usuario no encontrado", content = @Content)
    })
    @GetMapping("/user/type")
    public ResponseEntity<Map<String, String>> getUserSubscription(@AuthenticationPrincipal UserBase user) {
        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Usuario no encontrado"));
        }

        Subscription subscription = user.getSubscription();
        if (subscription == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "No hay suscripción asociada al usuario"));
        }

        String type = subscription.getType().name(); // Obtiene el tipo de suscripción

        Map<String, String> response = Map.of("planType", type);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/pay")
    public ResponseEntity<?> paySubscription(@AuthenticationPrincipal UserBase user, @RequestBody PaymentRequest paymentRequest) {
        try {
            // Procesar el pago
            PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);

            // Activar la suscripción si el pago es exitoso
            if (paymentResponse.isSuccessful()) {
                subscriptionService.activateSubscription(user, paymentRequest.getSubscriptionType(), paymentRequest.getDurationDays());
                return ResponseEntity.ok("Suscripción activada correctamente");
            } else {
                return ResponseEntity.badRequest().body("Error en el pago");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error en el servidor");
        }
    }

}
