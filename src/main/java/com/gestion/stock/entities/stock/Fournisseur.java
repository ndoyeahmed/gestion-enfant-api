package com.gestion.stock.entities.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestion.stock.entities.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"phone"})
)
@Entity
public class Fournisseur extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String code;
    @Column(name = "first_name")
    String firstName;
    @Column(name = "last_name")
    String lastName;
    @Column(name = "phone")
    String telephone;
    @Column(name = "status")
    Boolean etat;
    @Lob
    @Column(name = "address")
    String adresse;

    @JsonProperty("nomComplet")
    public String getFournisseur() {
        return firstName + " " + lastName + " " + telephone;
    }
}
