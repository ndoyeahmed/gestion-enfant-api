package com.gestion.stock.entities.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestion.stock.entities.Auditable;
import com.gestion.stock.entities.stock.Fournisseur;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
public class Commande extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String code;

    @Column(name = "status")
    Integer etat;

    @Column(name = "total")
    Integer total;

    String adresseLivraison;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("commandes")
    Client client;

    @ManyToOne
    @JoinColumn
    Fournisseur fournisseur;

    @NotNull
    @Size(min = 1)
    @JsonManagedReference
    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    Set<LineCommande> details = new HashSet<>();

    @JsonProperty("description")
    public String description() {
        return "Commande " + (
                client != null ? " Client " + client.getClient() :
                        fournisseur != null ? " Fournisseur " + fournisseur.getFournisseur() : "")
                + ", Date " + createdDate.toString();
    }
}
