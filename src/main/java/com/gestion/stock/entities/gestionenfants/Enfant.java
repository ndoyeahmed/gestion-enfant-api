package com.gestion.stock.entities.gestionenfants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestion.stock.entities.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Enfant extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String matricule;
    @Lob

    private String photo;
    private String nom;
    private String prenom;
    private Timestamp dateNaissance;
    private String adresse;
    private String telephone;
    private String genre;
    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    @ManyToOne
    @JoinColumn(name = "site", referencedColumnName = "id")
    private Site site;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dossier", referencedColumnName = "id")
    @JsonIgnore
    private Dossier dossier;
}
