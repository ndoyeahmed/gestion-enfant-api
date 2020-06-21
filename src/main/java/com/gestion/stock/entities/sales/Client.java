package com.gestion.stock.entities.sales;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestion.stock.entities.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"phone"})
)
@Entity
public class Client extends Auditable<String> {
    @JsonIgnoreProperties("client")
    @OneToMany(mappedBy = "client")
    @Where(clause = "status = 0")
    public Set<Commande> commandes = new HashSet<>();
    @ManyToOne
    @JoinColumn
    protected TypeClient typeClient;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String code;
    @Column(name = "first_name")
    @NotNull
    String firstName;
    @Column(name = "last_name")
    @NotNull
    String lastName;
    @Column(name = "phone")
    @NotNull
    String telephone;
    @Column(name = "status")
    Boolean etat;
    @Column(columnDefinition = "boolean default false")
    Boolean archive;
    @Column(name = "adresse")
    String adresse;

    @JsonProperty("nomComplet")
    public String getClient() {
        return firstName + " " + lastName + " " + telephone;
    }
}
