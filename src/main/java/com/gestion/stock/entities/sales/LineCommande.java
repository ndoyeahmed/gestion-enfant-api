package com.gestion.stock.entities.sales;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gestion.stock.entities.stock.Produit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"commande_id", "produit_id"})
)
public class LineCommande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "commande_id", referencedColumnName = "id")
    Commande commande;

    @ManyToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "id")
    Produit produit;

    Double prix;

    int quantite;
}
