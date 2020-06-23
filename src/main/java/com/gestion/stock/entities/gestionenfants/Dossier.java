package com.gestion.stock.entities.gestionenfants;

import com.gestion.stock.entities.Auditable;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@ToString
public class Dossier extends Auditable<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String libelle;
    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "enfant", referencedColumnName = "id")
    private Enfant enfant;

    @OneToMany(mappedBy = "dossier", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
//    @Fetch(value = FetchMode.SELECT)
    private List<Document> documents;
}
