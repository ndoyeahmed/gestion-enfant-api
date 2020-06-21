package com.gestion.stock.services.stock;

import com.gestion.stock.entities.stock.Categorie;
import com.gestion.stock.repositories.stock.CategorieRepository;
import com.gestion.stock.repositories.stock.ProduitRepository;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Log
public class ProduitService {
    private ProduitRepository produitRepository;
    private CategorieRepository categorieRepository;

    public ProduitService(ProduitRepository produitRepository,
                          CategorieRepository categorieRepository) {
        this.produitRepository = produitRepository;
        this.categorieRepository = categorieRepository;
    }

    public Categorie addCategorie(Categorie categorie) {
        return null;
    }

    public List<Categorie> allCategorie() {
        return categorieRepository.findAll();
    }
}
