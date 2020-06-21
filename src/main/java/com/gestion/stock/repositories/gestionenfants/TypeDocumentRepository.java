package com.gestion.stock.repositories.gestionenfants;

import com.gestion.stock.entities.gestionenfants.TypeDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeDocumentRepository extends JpaRepository<TypeDocument, Long> {
    Optional<List<TypeDocument>> findAllByArchive(Boolean archive);
}
