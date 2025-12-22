package com.techsolution.gestion_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techsolution.gestion_app.controller.dto.PaymentRequest;
import com.techsolution.gestion_app.features.payment.adapter.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final PayPalAdapter payPalAdapter;
    private final YapeAdapter yapeAdapter;
    private final PlinAdapter plinAdapter;
    private final com.techsolution.gestion_app.service.PaymentConfigService paymentConfigService;

    @PostMapping("/order/{orderId}")
    public ResponseEntity<String> processPayment(@PathVariable Long orderId, @RequestBody PaymentRequest req) {
        double monto = req.getMonto();
        String metodo = req.getMetodo().toLowerCase();

        var config = paymentConfigService.getConfig();

        // verificamos si la pasarela está habilitada en la configuración
        if ((metodo.equals("paypal") && !config.isPaypalActivo()) ||
            (metodo.equals("yape") && !config.isYapeActivo()) ||
            (metodo.equals("plin") && !config.isPlinActivo())) {
            return ResponseEntity.badRequest().body("La pasarela " + metodo + " está deshabilitada.");
        }

        var gateway = switch (metodo) {
            case "paypal" -> payPalAdapter;
            case "yape" -> yapeAdapter;
            case "plin" -> plinAdapter;
            default -> null;
        };

        if (gateway == null) {
            return ResponseEntity.badRequest().body("Método no válido. Usa: paypal, yape o plin.");
        }

        paymentService.setGateway(gateway);
        boolean ok = paymentService.processPayment(monto);
        return ResponseEntity.ok(ok ? "Pago realizado con éxito" : "No se pudo procesar el pago");
    }

    @PostMapping("/config/{gateway}/enable")
    public ResponseEntity<String> enableGateway(@PathVariable String gateway) {
        toggleGateway(gateway, true);
        return ResponseEntity.ok(gateway + " habilitado");
    }

    @PostMapping("/config/{gateway}/disable")
    public ResponseEntity<String> disableGateway(@PathVariable String gateway) {
        toggleGateway(gateway, false);
        return ResponseEntity.ok(gateway + " deshabilitado");
    }

    private void toggleGateway(String gateway, boolean enabled) {
        gateway = gateway.toLowerCase();
        // actualizamos la configuración persistente
        var cfg = paymentConfigService.getConfig();
        switch (gateway) {
            case "paypal" -> cfg.setPaypalActivo(enabled);
            case "yape" -> cfg.setYapeActivo(enabled);
            case "plin" -> cfg.setPlinActivo(enabled);
            default -> throw new IllegalArgumentException("Pasarela no válida: " + gateway);
        }
        paymentConfigService.updateConfig(cfg.isPaypalActivo(), cfg.isYapeActivo(), cfg.isPlinActivo());

        // sincronizamos el estado en los adapters en memoria
        payPalAdapter.setEnabled(cfg.isPaypalActivo());
        yapeAdapter.setEnabled(cfg.isYapeActivo());
        plinAdapter.setEnabled(cfg.isPlinActivo());
    }
}
