package com.gestion.stock.entities.admin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gestion.stock.entities.Auditable;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class ProfilUtilisateur extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Timestamp date;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Utilisateur utilisateur;

    @ManyToOne
    @JoinColumn
    private Profil profil;
}
