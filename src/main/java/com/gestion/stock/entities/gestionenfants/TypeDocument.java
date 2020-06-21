package com.gestion.stock.entities.gestionenfants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class TypeDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    @OneToMany(mappedBy = "typeDocument")
    @JsonIgnore
    private List<Document> documents;
}
