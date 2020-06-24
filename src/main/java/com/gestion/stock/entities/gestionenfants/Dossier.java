package com.gestion.stock.entities.gestionenfants;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.gestion.stock.entities.Auditable;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@ToString
@JsonFilter("documentsFilter")
public class Dossier extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String libelle;
    private Integer nombreDocAFournir;
    private Integer nombreDocFournis;
    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "enfant", referencedColumnName = "id")
    private Enfant enfant;

    @OneToMany(mappedBy = "dossier")
    private List<Document> documents;
}
