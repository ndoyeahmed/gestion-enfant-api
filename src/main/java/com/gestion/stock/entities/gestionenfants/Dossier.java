package com.gestion.stock.entities.gestionenfants;

import com.gestion.stock.entities.Auditable;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString
public class Dossier extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String libelle;
    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    @OneToOne(mappedBy = "dossier")
    private Enfant enfant;

    @OneToMany(mappedBy = "dossier")
    private List<Document> documents;
}
