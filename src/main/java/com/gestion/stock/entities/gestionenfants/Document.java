package com.gestion.stock.entities.gestionenfants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String libelle;
    @Lob
    private String document;
    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    @ManyToOne
    @JoinColumn(name = "dossier", referencedColumnName = "id")
    @JsonIgnore
    private Dossier dossier;

    @ManyToOne
    @JoinColumn(name = "typeDocument", referencedColumnName = "id")
    private TypeDocument typeDocument;
}
