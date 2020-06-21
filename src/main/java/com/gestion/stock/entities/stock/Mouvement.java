package com.gestion.stock.entities.stock;

import com.gestion.stock.entities.Auditable;
import com.gestion.stock.entities.sales.Commande;
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
public class Mouvement extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String code;

    @Column(name = "status")
    Integer etat;

    @Column(name = "total")
    Integer total;

    String type;

    String description;

    @ManyToOne
    @JoinColumn
    Commande commande;

    @NotNull
    @Size(min = 1)
    @OneToMany(mappedBy = "mouvement", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, orphanRemoval = true)
    Set<LineMouvement> details = new HashSet<>();

    @ManyToOne
    @JoinColumn
    Mouvement mouvement;

    public void addDetail(LineMouvement detail) {
        if (details.contains(detail)) return;
        details.add(detail);
        detail.setMouvement(this);
    }

    public void removeDetail(LineMouvement detail) {
        if (details.contains(detail)) return;
        details.remove(detail);
        detail.setMouvement(null);
    }
}
