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
public class Categorie extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String code;
    @NotNull
    String nom;
    @Lob
    String description;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    Categorie categorie;

}
