package com.gestion.stock.entities.stock;

import com.gestion.stock.entities.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
public class Produit extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String code;
    @NotNull
    String libelle;
    @Lob
    String description;

    Double prixUnitaire;
    Double prixVente;

    @NotNull
    Integer quantite;
    @NotNull
    Integer seuil;

    @Lob
    String image;

    @ManyToOne
    @JoinColumn
    Categorie categorie;

}
