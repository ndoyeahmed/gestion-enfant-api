package com.gestion.stock.entities.sales;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.gestion.stock.entities.stock.Produit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@NoArgsConstructor
@Entity
@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"vente_id", "produit_id"})
)
public class LineVente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "vente_id", referencedColumnName = "id")
    Vente vente;

    @ManyToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "id")
    Produit produit;

    Double prix;

    int quantite;
}
