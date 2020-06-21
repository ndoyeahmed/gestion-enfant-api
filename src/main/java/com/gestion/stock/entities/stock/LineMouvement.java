package com.gestion.stock.entities.stock;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"mouvement_id", "produit_id"})
)
public class LineMouvement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnoreProperties("details")
    @ManyToOne
    @JoinColumn(name = "mouvement_id", referencedColumnName = "id")
    Mouvement mouvement;

    @ManyToOne
    @JoinColumn(name = "produit_id", referencedColumnName = "id")
    Produit produit;

    Double prix;

    int quantite;

    @JsonProperty("type")
    public String getType() {
        return mouvement.getType();
    }

    @JsonProperty("date")
    public Timestamp getDate() {
        return mouvement.getCreatedDate();
    }

}
