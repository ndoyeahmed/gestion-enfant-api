package com.gestion.stock.web.controllers.stock;

import com.gestion.stock.services.stock.ProduitService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log
@RequestMapping("/api")
public class ProduitController {

    private ProduitService produitService;

    @Autowired
    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping("/categories")
    public ResponseEntity<?> allCategorie() {
        return ResponseEntity.ok(produitService.allCategorie());
    }
}
