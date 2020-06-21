package com.gestion.stock.entities.gestionenfants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestion.stock.entities.Auditable;
import com.gestion.stock.entities.admin.Utilisateur;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Site extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String libelle;
    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    @ManyToOne
    @JoinColumn(name = "utilisateur", referencedColumnName = "id")
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "site")
    @JsonIgnore
    private List<Enfant> enfants;
}
