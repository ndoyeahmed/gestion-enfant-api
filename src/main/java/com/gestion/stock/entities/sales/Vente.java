package com.gestion.stock.entities.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestion.stock.entities.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@Entity
public class Vente extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String code;

    @Column(name = "status")
    Integer etat;

    @Column(name = "total")
    Integer total;

    @NotNull
    String modePaiement;

    @NotNull
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties("commandes")
    Client client;

    @ManyToOne
    @JoinColumn
    Commande commande;

    @NotNull
    @Size(min = 1)
    @JsonManagedReference
    @OneToMany(mappedBy = "vente", cascade = CascadeType.ALL)
    Set<LineVente> details = new HashSet<>();

    @JsonProperty("description")
    public String description() {
        return "Vente de la commande du " +
                " Client " + this.getClient().getClient() +
                ", Date " + createdDate.toString();
    }
}
