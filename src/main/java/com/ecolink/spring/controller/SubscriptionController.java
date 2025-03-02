package com.ecolink.spring.controller;

import com.ecolink.spring.entity.SubscriptionType;
import com.ecolink.spring.entity.UserBase;
import com.ecolink.spring.service.SubscriptionService;
import com.ecolink.spring.service.UserBaseService;
import com.ecolink.spring.entity.Subscription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService, UserBaseService userBaseService) {
        this.subscriptionService = subscriptionService;
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
        System.out.println("Usuario autenticado: " + user);

        Subscription subscription = user.getSubscription();
        if (subscription == null) {
            subscription = new Subscription();
            subscription.setUser(user);
        }

        subscription.setType(type);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(durationDays));
        subscriptionService.save(subscription);

        return ResponseEntity.ok("Suscripción activada correctamente");
    }

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

}
